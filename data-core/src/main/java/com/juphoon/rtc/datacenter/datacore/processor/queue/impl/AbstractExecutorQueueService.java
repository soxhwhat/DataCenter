package com.juphoon.rtc.datacenter.datacore.processor.queue.impl;

import com.juphoon.rtc.datacenter.datacore.api.BaseContext;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractProcessor;
import com.juphoon.rtc.datacenter.datacore.processor.queue.AbstractQueueService;
import com.juphoon.rtc.datacenter.datacore.processor.queue.QueueServiceConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Author:Jiahui.huang
 * @Date: 2022/6/28 19:23
 * @Description:增加FixedThreadPool线程池，用于处理事件队列
 */
@Slf4j
public class AbstractExecutorQueueService<T extends BaseContext> extends AbstractQueueService<T> {
    private final static AtomicInteger THREAD_NUMBER = new AtomicInteger(1);

    private ThreadPoolExecutor threadPoolExecutor;

    public AbstractExecutorQueueService(AbstractProcessor<T> processor, QueueServiceConfig config) {
        super(processor, config);
    }

    @Override
    public void onSubmit(T ec) throws Exception {
        log.info("submit ec:{}", ec);
        //提交任务到线程池,并返回执行结果
        threadPoolExecutor.submit(
                () -> {
                    log.info("ec:{} onSubmit {}", ec, Thread.currentThread().getName());
                    getProcessor().process(ec);
                }
        );
    }

    @Override
    public void init(QueueServiceConfig config) {
        /*
          初始化线程池配置
         */
        log.info("init queue thread pool {}", getProcessor().getId());

        threadPoolExecutor = new ThreadPoolExecutor(
                config.getCorePoolSize(),
                config.getMaxPoolSize(),
                config.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(config.getQueueSize()),
                r -> new Thread(r, getProcessor().getId() + "-queue-pool-" + THREAD_NUMBER.getAndIncrement()),
                new ThreadPoolExecutor.AbortPolicy()
        );

        /**
         * 线程预热，预热线程池中的线程，让线程池中的线程尽快的启动。
         * 选择策略为启动所有核心线程
         */
        threadPoolExecutor.prestartAllCoreThreads();

        log.info("init queue thread pool {} success", getProcessor().getId());
    }


}
