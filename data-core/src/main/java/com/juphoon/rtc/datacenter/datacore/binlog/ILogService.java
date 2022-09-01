package com.juphoon.rtc.datacenter.datacore.binlog;

import com.juphoon.rtc.datacenter.datacore.api.BaseContext;
import com.juphoon.rtc.datacenter.datacore.api.IService;
import com.juphoon.rtc.datacenter.datacore.handler.IHandler;

import java.util.List;

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
 */
public interface ILogService<T extends BaseContext> extends IService {
    /**
     * 保存新事件
     *
     * @param t
     */
    void save(T t);

    /**
     * 保存重做事件
     *
     * @param t
     * @param handler
     */
    void saveRedo(T t, IHandler handler);

    /**
     * 批量保存新事件
     *
     * @param t
     */
    void save(List<T> t);

    /**
     * 删除事件
     * todo 增加 processor
     * @param t
     */
    void remove(T t);

    /**
     * 删除事件
     *
     * @param id
     */
    void remove(Long id);

    /**
     * 批量删除记录
     *
     * @param ids
     */
    void remove(List<Long> ids);

    /**
     * 查询指定条目记录
     *
     * @param size
     * @return
     */
    List<T> find(int size);

    /**
     * 更新失败计数
     * @param t
     */
    void updateRetryCount(T t);
}
