package com.juphoon.rtc.datacenter.datacore.processor.queue;

import com.google.common.collect.Sets;
import com.juphoon.rtc.datacenter.datacore.api.BaseContext;
import com.juphoon.rtc.datacenter.datacore.exception.JrtcRepeatedSubmitEventException;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractProcessor;
import com.juphoon.rtc.datacenter.datacore.utils.MetricUtils;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public abstract class AbstractQueueService<T extends BaseContext> implements IQueueService<T> {
    private AbstractProcessor<T> processor;

    private final Set<Long> eventIndex = Sets.newConcurrentHashSet();

    public AbstractQueueService(AbstractProcessor<T> processor, QueueServiceConfig config) {
        this.processor = processor;
        init(config);
    }

    /**
     * 获取处理器
     */
    public AbstractProcessor<T> getProcessor() {
        return processor;
    }

    /**
     * 处理事件提交
     *
     * @param ec
     * @throws Exception
     */
    public abstract void onSubmit(T ec) throws Exception;

    /**
     * 1. 线程1 数据被消费但是还没来得及从本地缓存中删除，以及从Queue中清除index
     * 2. 线程2 数据被loader加载
     * 3. 线程1 删除缓存、清除index
     * 4. 线程2 submit到queue
     *
     * 这时候数据有可能被重复消费，因此这里加锁，锁住队列
     *
     * @param ec
     * @throws Exception
     */
    @Override
    public synchronized void submit(T ec) throws Exception {
        log.debug("ec:{}", ec);

        Timer.Sample sample2 = Timer.start();
        // 去重
        if (eventIndex.contains(ec.getId())) {
            throw new JrtcRepeatedSubmitEventException(ec.getId() + " 重复提交");
        }
        sample2.stop(MetricUtils.get("queueService.eventIndex"));

        Timer.Sample sample3 = Timer.start();
        /*
         * 先提交再写入hashSet中
         * 如果先放入set中再提交，线程池满执行拒绝策略会导致set中的数据无法清除
         */
        onSubmit(ec);
        sample3.stop(MetricUtils.get("queueService.onSubmit"));

        Timer.Sample sample4 = Timer.start();
        eventIndex.add(ec.getId());
        sample4.stop(MetricUtils.get("queueService.eventIndex.add"));
    }

    /**
     * 1. 线程1 数据被消费但是还没来得及从本地缓存中删除，以及从Queue中清除index
     * 2. 线程2 数据被loader加载
     * 3. 线程1 删除缓存、清除index
     * 4. 线程2 submit到queue
     *
     * 这时候数据有可能被重复消费，因此这里加锁，锁住队列
     *
     * @param ec
     */
    @Override
    public synchronized void success(T ec) {
        log.debug("ec:{},{}", ec, eventIndex.size());

        eventIndex.remove(ec.getId());
    }
}