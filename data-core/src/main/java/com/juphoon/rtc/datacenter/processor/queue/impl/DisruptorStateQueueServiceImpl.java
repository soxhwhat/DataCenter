package com.juphoon.rtc.datacenter.processor.queue.impl;

import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.processor.AbstractStateProcessor;
import com.juphoon.rtc.datacenter.processor.queue.AbstractStateQueueService;
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
public class DisruptorStateQueueServiceImpl extends AbstractStateQueueService {

    private Disruptor<ContextHolder<StateContext>> disruptor;

    public DisruptorStateQueueServiceImpl(AbstractStateProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }

    @Override
    public void init(QueueServiceConfig config) {
        EventFactory<ContextHolder<StateContext>> eventFactory = ContextHolder::new;

        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        EventHandler<ContextHolder<StateContext>> eventHandler = (holder, sequence, endOfBatch) -> getProcessor().process(holder.getContext());

        disruptor = new Disruptor<>(eventFactory, config.getQueueSize(), threadFactory,
                ProducerType.SINGLE, new BlockingWaitStrategy());

        disruptor.handleEventsWith(eventHandler);

        disruptor.start();
    }


    /**
     * 提交事件
     * TODO TODO 修改为正确的数据格式
     * @param context
     * @return
     * @throws Exception
     */
    @Override
    public void onSubmit(StateContext context) {
        try {
            disruptor.publishEvent((holder, l) -> holder.setContext(context));
        } catch (Exception e) {
            log.error("push内存队列失败:", e);
            throw e;
        }
    }

}