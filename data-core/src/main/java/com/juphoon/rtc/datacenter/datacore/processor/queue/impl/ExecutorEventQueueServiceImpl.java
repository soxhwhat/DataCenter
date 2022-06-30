package com.juphoon.rtc.datacenter.datacore.processor.queue.impl;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractEventProcessor;
import com.juphoon.rtc.datacenter.datacore.processor.queue.QueueServiceConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>事件异步消费队列实现</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 7/11/22 11:09 AM
 *
 */
@Slf4j
public class ExecutorEventQueueServiceImpl extends AbstractExecutorQueueService<EventContext> {
    public ExecutorEventQueueServiceImpl(AbstractEventProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }
}
