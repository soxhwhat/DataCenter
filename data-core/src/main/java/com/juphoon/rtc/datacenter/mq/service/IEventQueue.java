package com.juphoon.rtc.datacenter.mq.service;

import com.juphoon.rtc.datacenter.mq.EventQueueConfig;

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
    void init(EventQueueConfig config, AbstractEventQueueService queueService);

    /**
     * 获取队列大小
     * @return
     */
    int getSize();
}
