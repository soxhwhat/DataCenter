package com.juphoon.rtc.datacenter.binlog.mapper;

import com.juphoon.rtc.datacenter.binlog.entity.EventBinLogPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ajian.zheng
 * @data 2022-05-12
 */
public interface EventLogMapper {
    /**
     * 初始化表
     */
    void createTable();

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
}
