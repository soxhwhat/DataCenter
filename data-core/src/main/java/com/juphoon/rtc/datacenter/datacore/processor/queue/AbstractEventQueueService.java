package com.juphoon.rtc.datacenter.datacore.processor.queue;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractEventProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public abstract class AbstractEventQueueService extends AbstractQueueService<EventContext> {
    public AbstractEventQueueService(AbstractEventProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }
}