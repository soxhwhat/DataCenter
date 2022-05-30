package com.juphoon.rtc.datacenter.binlog;

import com.juphoon.rtc.datacenter.handler.IHandler;

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
public interface ILogService<T> {
    /**
     * 保存新事件
     * todo 删除
     * @param t
     */
    void save(T t);

    /**
     * 删除事件
     * todo 增加 processor
     * @param t
     */
    void remove(T t);

    /**
     * 保存重做日志
     * todo, add processor
     * @param t
     * @param handler
     */
    void saveRedo(T t, IHandler<T> handler);

    /**
     * 删除事件
     *
     * @param t
     */
    void removeRedo(T t);
}
