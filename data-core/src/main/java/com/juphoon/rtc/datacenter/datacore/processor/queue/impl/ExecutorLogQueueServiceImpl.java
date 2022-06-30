package com.juphoon.rtc.datacenter.datacore.processor.queue.impl;

import com.juphoon.rtc.datacenter.datacore.api.LogContext;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractLogProcessor;
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
public class ExecutorLogQueueServiceImpl extends AbstractExecutorQueueService<LogContext> {
    public ExecutorLogQueueServiceImpl(AbstractLogProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }
}
