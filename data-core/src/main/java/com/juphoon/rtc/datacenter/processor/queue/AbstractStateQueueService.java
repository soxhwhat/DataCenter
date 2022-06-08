package com.juphoon.rtc.datacenter.processor.queue;

import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.processor.AbstractStateProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public abstract class AbstractStateQueueService extends AbstractQueueService<StateContext> {
    public AbstractStateQueueService(AbstractStateProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }
}