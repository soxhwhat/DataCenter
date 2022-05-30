package com.juphoon.rtc.datacenter.processor.queue.impl;

import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.processor.queue.QueueServiceConfig;
import com.juphoon.rtc.datacenter.processor.AbstractLogProcessor;
import com.juphoon.rtc.datacenter.processor.queue.AbstractLogQueueService;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public class DisruptorLogQueueServiceImpl extends AbstractLogQueueService {

    private Disruptor<LogContext> disruptor;

    public DisruptorLogQueueServiceImpl(AbstractLogProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }

    @Override
    public void init(QueueServiceConfig config) {
        EventFactory<LogContext> eventFactory = LogContext::new;

        ThreadFactory threadFactory = new ThreadFactory() {
            private int counter = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "disruptor-" + counter++);
            }
        };

        EventHandler<LogContext> eventHandler = (ec, sequence, endOfBatch) -> getProcessor().process(ec);

        disruptor = new Disruptor<>(eventFactory, config.getQueueSize(), threadFactory,
                ProducerType.SINGLE, new BlockingWaitStrategy());

        disruptor.handleEventsWith(eventHandler);

        disruptor.start();
    }


    /**
     * 提交事件
     *
     * @param ec
     * @return
     * @throws Exception
     */
    @Override
    public void onSubmit(LogContext ec) {
        try {
            disruptor.publishEvent((eventContext, l) -> {
                eventContext.setLogs(ec.getLogs());
                eventContext.setBeginTimestamp(ec.getBeginTimestamp());
                eventContext.setCreatedTimestamp(ec.getBeginTimestamp());
                eventContext.setRetryCount(ec.getRetryCount());
            });
        } catch (Exception e) {
            log.error("push内存队列失败:", e);
            throw e;
        }
    }

}