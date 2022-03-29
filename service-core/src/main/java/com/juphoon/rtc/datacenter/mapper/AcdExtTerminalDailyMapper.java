package com.juphoon.rtc.datacenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdCallInfoStatExtTerminalDailyPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>jrtc_acd_ext_terminal_daily表的mapper类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Mapper
public interface AcdExtTerminalDailyMapper extends BaseMapper<AcdCallInfoStatExtTerminalDailyPO> {
    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(AcdCallInfoStatExtTerminalDailyPO record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    AcdCallInfoStatExtTerminalDailyPO selectByPrimaryKey(Long id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(AcdCallInfoStatExtTerminalDailyPO record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(AcdCallInfoStatExtTerminalDailyPO record);
}