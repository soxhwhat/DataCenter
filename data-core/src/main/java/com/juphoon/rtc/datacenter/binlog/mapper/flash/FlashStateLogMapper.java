package com.juphoon.rtc.datacenter.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.binlog.entity.LogBinLogPO;
import com.juphoon.rtc.datacenter.binlog.entity.StateBinLogPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ajian.zheng
 * @data 2022-05-12
 */
@Mapper
@Component
public interface FlashStateLogMapper {
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
    int save(StateBinLogPO po);

    /**
     * 批量保存新事件
     *
     * @param list
     * @return lines
     */
    int saveList(List<StateBinLogPO> list);

    /**
     * 删除事件
     * todo 增加 processor
     * @param id
     */
    void remove(long id);

    /**
     * 查询指定数量记录
     *
     * @param size
     * @return
     */
    List<StateBinLogPO> find(int size);

    /**
     * 通过 contentId 查询
     * @param id
     * @return
     */
    StateBinLogPO findById(long id);
}
