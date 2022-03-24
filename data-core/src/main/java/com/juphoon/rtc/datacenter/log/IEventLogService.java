package com.juphoon.rtc.datacenter.log;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.juphoon.rtc.datacenter.api.EventContext;

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
public interface IEventLogService {

    /**
     * 保存新事件
     *
     * @param ec
     */
    void saveEvent(EventContext ec);

    /**
     * 删除事件
     *
     * @param ec
     */
    void removeEvent(EventContext ec);

    /**
     * 事件更新入队标记
     * submit事件可能因为队列满无法入队，此时会将事件直接写DB，待扫描线程定时扫描获取插队
     * 插队成功则更新标记，避免
     *
     * @param ec
     */
    void updateEventQueued(EventContext ec);

    /**
     * 分页查找未入队事件
     *
     * @return
     */
    IPage<EventContext> findUnqueuedEvents();

    /**
     * 启动时逻辑
     * 应该需要将db中所有未消费事件读取出来放入队列
     *
     * 这里有几种做法
     * 1. 将所有事件标记为未入队
     * 2. 增加轮次字段，每次启动都用时间戳作为轮次，这样只需要将非本轮的数据load出来即可
     * 3. 配合 @link findUnqueuedEvents()
     * 4. TODO
     *
     */
    void startup();

    /**
     * 初始化表
     */
    void initTables();
}
