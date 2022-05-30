package com.juphoon.rtc.datacenter.processor;


import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.INamed;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.event.queue.IEventQueueService;
import com.juphoon.rtc.datacenter.event.storage.IEventLogService;
import com.juphoon.rtc.datacenter.handler.IEventHandler;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 9:44
 * @update:
 * <p>1. 2022-03-24. ajian.zheng 增加可命名接口</p>
 */
public interface IEventProcessor extends INamed {
    /**
     * 设置处理器名
     * @return 处理器id
     */
    ProcessorId processorId();

    /**
     * 设置事件存储
     *
     * @return
     */
//    IEventLogService eventLogService();

    /**
     * 构造事件消费队列服务
     *
     * @return
     */
    IEventQueueService buildMyEventQueueService();

    /**
     * 获取事件消费队列服务
     * @return
     */
    IEventQueueService eventQueueService();

    /**
     * 处理事件
     *
     * @param ec
     * @throws Exception
     */
    void process(EventContext ec);

    /**
     * 组装 handler
     * @param handler
     */
    void addEventHandler(IEventHandler handler);
}
