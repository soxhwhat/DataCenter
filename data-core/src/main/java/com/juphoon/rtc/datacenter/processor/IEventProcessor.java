package com.juphoon.rtc.datacenter.processor;


import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.INamed;
import com.juphoon.rtc.datacenter.event.queue.IEventQueueService;
import com.juphoon.rtc.datacenter.event.storage.IEventLogService;
import com.juphoon.rtc.datacenter.event.storage.IRedoLogService;
import com.juphoon.rtc.datacenter.handler.IEventHandler;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 9:44
 * @update:
 * <p>1. 2022-03-24. ajian.zheng 增加可命名接口</p>
 */
public interface IEventProcessor extends INamed {
    /**
     * 处理事件
     *
     * @param ec
     * @throws Exception
     */
    void process(EventContext ec);


    /**
     * 组装 eventLogService
     *
     * @param eventLogService
     */
    void setEventLogService(IEventLogService eventLogService);

    /**
     * 组装 redoLogService
     *
     * @param redoLogService
     */
    void setRedoLogService(IRedoLogService redoLogService);

    /**
     * 组装 queueService
     * @param queueService
     */
    void setEventQueueService(IEventQueueService queueService);

    /**
     * 组装 handler
     * @param handler
     */
    void addEventHandler(IEventHandler handler);

}
