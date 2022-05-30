package com.juphoon.rtc.datacenter.event.queue;

import com.juphoon.rtc.datacenter.processor.queue.AbstractEventQueueService;
import com.juphoon.rtc.datacenter.processor.queue.QueueServiceConfig;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
public interface IEventQueue {
    /**
     * 初始化线程池
     *
     * @param config
     * @param queueService
     */
    void init(QueueServiceConfig config, AbstractEventQueueService queueService);

    /**
     * 获取队列大小
     * @return
     */
    int getSize();
}
