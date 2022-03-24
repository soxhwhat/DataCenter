package com.juphoon.rtc.datacenter.log;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.IEventHandler;

/**
 * <p>mq日志服务</p>
 * <p>用于事件高可靠，</p>
 * <li>1. processor 离线保存未消费事件</li>
 * <li>2. processor 消息处理成功消息清理</li>
 * <li>3. handler 消息处理失败记录</li>
 * <li>4. handler 消息重做成功记录清理</li>
 * <li>5. 从db加载处理失败消息</li>
 *
 * @date 20222-03-16
 * @author zhiwei.zhai
 *
 *
 */
public interface IRedoEventLogService {
    /**
     * 事件重做记录
     *
     * @param ec
     * @param handler
     */
    void saveRedoEvent(EventContext ec, IEventHandler handler);

    /**
     * 清理重做记录
     *
     * @param ec
     * @param handler
     */
    void removeRedoEvent(EventContext ec, IEventHandler handler);


    /**
     * 初始化表
     */
    void initTables();
}
