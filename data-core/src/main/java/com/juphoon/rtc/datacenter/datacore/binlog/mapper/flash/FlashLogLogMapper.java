package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.binlog.entity.LogBinLogPO;

import java.util.List;

/**
 * @author ajian.zheng
 * @data 2022-05-12
 */
public interface FlashLogLogMapper {
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
     * @param po
     * @return lines
     */
    int save(LogBinLogPO po);

    /**
     * 批量保存新事件
     *
     * @param list
     * @return lines
     */
    int saveList(List<LogBinLogPO> list);

    /**
     * 删除事件
     * todo 增加 processor
     * @param id
     */
    void remove(long id);

    /**
     * 通过 contentId 查询
     * @param id
     * @return
     */
    LogBinLogPO findById(long id);

    /**
     * 查询指定数量记录
     *
     * @param size
     * @return
     */
    List<LogBinLogPO> find(int size);

    /**
     * 更新失败计数
     * @param po
     */
    void updateRetryCount(LogBinLogPO po);
}
