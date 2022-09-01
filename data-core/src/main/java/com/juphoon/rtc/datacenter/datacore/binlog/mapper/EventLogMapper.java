package com.juphoon.rtc.datacenter.datacore.binlog.mapper;

import com.juphoon.rtc.datacenter.datacore.binlog.entity.EventBinLogPO;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ajian.zheng
 * @data 2022-05-12
 */
public interface EventLogMapper {
    /**
     * 初始化表
     * @throws SQLException
     */
    void createTable() throws SQLException;

    /**
     * 删除表
     */
    void dropTable();

    /**
     * 保存新事件
     *
     * @param event
     * @return lines
     */
    int save(EventBinLogPO event);

    /**
     * 批量保存新事件
     *
     * @param list
     * @return lines
     */
    int saveList(List<EventBinLogPO> list);

    /**
     * 删除事件
     * todo 增加 processor
     * @param id
     */
    void remove(long id);

    /**
     * 批量删除
     * @param list
     */
    void remove(List<Long> list);

    /**
     * 更新失败计数
     * @param po
     */
    void updateRetryCount(EventBinLogPO po);

    /**
     * 通过 contentId 查询
     * @param contentId
     * @return
     */
    EventBinLogPO findById(long contentId);

    /**
     * 查询指定数量记录
     *
     * @param size
     * @return
     */
    List<EventBinLogPO> find(int size);

    /**
     * 关闭连接
     */
    void stop();
}
