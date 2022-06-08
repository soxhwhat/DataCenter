package com.juphoon.rtc.datacenter.processor.queue;

import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.processor.AbstractLogProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public abstract class AbstractLogQueueService extends AbstractQueueService<LogContext> {
    public AbstractLogQueueService(AbstractLogProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }
}