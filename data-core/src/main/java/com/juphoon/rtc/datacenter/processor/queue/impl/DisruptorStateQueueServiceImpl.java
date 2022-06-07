package com.juphoon.rtc.datacenter.processor.queue.impl;

import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.processor.AbstractStateProcessor;
import com.juphoon.rtc.datacenter.processor.queue.AbstractStateQueueService;
import com.juphoon.rtc.datacenter.processor.queue.QueueServiceConfig;
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
public class DisruptorStateQueueServiceImpl extends AbstractStateQueueService {

    private Disruptor<StateContext> disruptor;

    public DisruptorStateQueueServiceImpl(AbstractStateProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }

    @Override
    public void init(QueueServiceConfig config) {
        EventFactory<StateContext> eventFactory = StateContext::new;

        ThreadFactory threadFactory = new ThreadFactory() {
            private int counter = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "disruptor-" + counter++);
            }
        };

        EventHandler<StateContext> eventHandler = (ec, sequence, endOfBatch) -> getProcessor().process(ec);

        disruptor = new Disruptor<>(eventFactory, config.getQueueSize(), threadFactory,
                ProducerType.SINGLE, new BlockingWaitStrategy());

        disruptor.handleEventsWith(eventHandler);

        disruptor.start();
    }


    /**
     * 提交事件
     * TODO TODO 修改为正确的数据格式
     * @param ec
     * @return
     * @throws Exception
     */
    @Override
    public void onSubmit(StateContext ec) {
        try {
            disruptor.publishEvent((eventContext, l) -> {
                eventContext.setState(ec.getState());
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