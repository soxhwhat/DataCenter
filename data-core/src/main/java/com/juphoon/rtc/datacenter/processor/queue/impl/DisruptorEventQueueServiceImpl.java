package com.juphoon.rtc.datacenter.processor.queue.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import com.juphoon.rtc.datacenter.processor.queue.AbstractEventQueueService;
import com.juphoon.rtc.datacenter.processor.queue.ContextHolder;
import com.juphoon.rtc.datacenter.processor.queue.QueueServiceConfig;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public class DisruptorEventQueueServiceImpl extends AbstractEventQueueService {

    private Disruptor<ContextHolder<EventContext>> disruptor;

    public DisruptorEventQueueServiceImpl(AbstractEventProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }

    @Override
    public void init(QueueServiceConfig config) {
        EventFactory<ContextHolder<EventContext>> eventFactory = ContextHolder::new;

        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        EventHandler<ContextHolder<EventContext>> eventHandler =
                (holder, sequence, endOfBatch) -> getProcessor().process(holder.getContext());

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
    public void onSubmit(EventContext ec) {
        try {
            disruptor.publishEvent((holder, l) -> holder.setContext(ec));
        } catch (Exception e) {
            log.error("push内存队列失败:", e);
            throw e;
        }
    }
}