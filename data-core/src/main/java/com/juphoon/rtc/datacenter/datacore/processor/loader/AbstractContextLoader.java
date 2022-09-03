package com.juphoon.rtc.datacenter.datacore.processor.loader;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.juphoon.rtc.datacenter.datacore.api.BaseContext;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.exception.JrtcRepeatedSubmitEventException;
import com.juphoon.rtc.datacenter.datacore.processor.IProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>记录加载器</p>
 * 1. 定时从sqlite中加载为成功消费的事件，投递到queue中
 * 2.
 * @author ajian.zheng@juphoon.com
 * @date 6/24/22 8:58 AM
 */
@Slf4j
public abstract class AbstractContextLoader<T extends BaseContext> implements IContextLoader<T> {
    public AbstractContextLoader(ILogService<T> logService, IProcessor<T> processor, ContextLoaderConfig config) {
        this.logService = logService;
        this.processor = processor;
        this.config = config;
    }

    /**
     * 日志服务
     */
    private ILogService<T> logService;

    /**
     * 所属 processor
     */
    private IProcessor<T> processor;

    /**
     * 配置
     */
    private ContextLoaderConfig config;

    /**
     * 线程池
     */
    private ScheduledExecutorService executor;

    /**
     * 待删除事件列表
     */
    private volatile Set<Long> deleteList = Sets.newConcurrentHashSet();

    /**
     * 添加待删除内容到待删除列表中
     *
     * @param t
     */
    public void deleteContext(T t) {
        if (t.isRedoEvent()) {
            return;
        }
        log.trace("deleteContext:{}", t.getId());
        deleteList.add(t.getId());
    }

    @Override
    public void start() {
        executor = new ScheduledThreadPoolExecutor(1,
                new ThreadFactoryBuilder().setNameFormat("ctx-loader-%d").build(),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        executor.scheduleWithFixedDelay(this::loadAndSubmit, config.getInitialDelaySeconds(), config.getDelaySeconds(), TimeUnit.SECONDS);
    }

    /**
     * todo 这里有很多优化的点
     * 1. 设置队列高低水位，处于高水位时，说明队列消费阻塞，可以不用执行load，节约资源
     * 2. 智能规避提交等
     */
    private void loadAndSubmit() {
        /// 避免交叉影响，搞个新队列继续接收
        Set<Long> set = deleteList;
        deleteList = Sets.newConcurrentHashSet();

        /// 先删除，再加载，避免加载出来的东西
        logService.remove(new ArrayList<>(set));
        set.clear();

        /// 此时再加载，不会加载到已经删除的事件了
        List<T> list = loadContexts(logService);

        log.debug("{} load {} records", processor.getId(), list.size());

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (T t : list) {
            try {
                /// 提交前再去重判断一下
                if (!deleteList.contains(t.getId())) {
                    log.debug("loadAndSubmit:{}", t.getId());
                    processor.queueService().submit(t);
                }
            } catch (JrtcRepeatedSubmitEventException e) {
                log.debug("repeat load:{}", e.getMessage());
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void stop() {
        if (null != executor) {
            executor.shutdown();

            // 等待线程池终止
            boolean isDone = true;
            do {
                try {
                    isDone = executor.awaitTermination(1, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!isDone);
        }
    }
}
