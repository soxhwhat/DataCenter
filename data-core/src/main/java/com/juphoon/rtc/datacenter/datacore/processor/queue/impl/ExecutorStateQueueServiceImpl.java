package com.juphoon.rtc.datacenter.datacore.processor.queue.impl;

import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractStateProcessor;
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
public class ExecutorStateQueueServiceImpl extends AbstractExecutorQueueService<StateContext> {
    public ExecutorStateQueueServiceImpl(AbstractStateProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }
}
