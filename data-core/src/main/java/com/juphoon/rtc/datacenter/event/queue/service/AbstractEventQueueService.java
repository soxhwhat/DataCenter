package com.juphoon.rtc.datacenter.event.queue.service;

import com.juphoon.rtc.datacenter.event.queue.IEventQueueService;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
public abstract class AbstractEventQueueService implements IEventQueueService {
    protected AbstractEventProcessor processor;

    /**
     * 设置处理器
     *
     * @param processor
     */
    public void setProcessor(AbstractEventProcessor processor) {
        this.processor = processor;
    }

    /**
     * 获取处理器
     */
    public AbstractEventProcessor getProcessor() {
        return processor;
    }


}