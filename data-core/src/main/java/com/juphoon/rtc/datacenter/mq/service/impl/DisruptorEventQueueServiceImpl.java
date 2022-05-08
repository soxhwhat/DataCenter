package com.juphoon.rtc.datacenter.mq.service.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.mq.EventQueueConfig;
import com.juphoon.rtc.datacenter.mq.service.AbstractEventQueueService;
import com.juphoon.rtc.datacenter.mq.service.IEventQueue;
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
public class DisruptorEventQueueServiceImpl extends AbstractEventQueueService implements IEventQueue {

    private Disruptor<EventContext> disruptor;

    private EventQueueConfig config;

    public DisruptorEventQueueServiceImpl(EventQueueConfig config) {
        this.config = config;
    }

    @Override
    public void init(EventQueueConfig config, AbstractEventQueueService queueService) {
        EventFactory<EventContext> eventFactory = EventContext::new;

        ThreadFactory threadFactory = new ThreadFactory() {
            private int counter = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "disruptor-" + counter++);
            }
        };

        EventHandler<EventContext> eventHandler = (ec, sequence, endOfBatch) -> getProcessor().onProcess(ec);

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
    public void submit(EventContext ec) {
        try {
            disruptor.publishEvent((eventContext, l) -> {
                eventContext.setEvent(ec.getEvent());
                eventContext.setEventList(ec.getEventList());
                eventContext.setBeginTimestamp(ec.getBeginTimestamp());
                eventContext.setCreatedTimestamp(ec.getBeginTimestamp());
                eventContext.setRetryCount(ec.getRetryCount());
            });
        } catch (Exception e) {
            log.error("push内存队列失败:", e);
            throw e;
        }
    }

    @Override
    public int getSize() {
        return Long.valueOf(disruptor.getRingBuffer().remainingCapacity()).intValue();
    }


}