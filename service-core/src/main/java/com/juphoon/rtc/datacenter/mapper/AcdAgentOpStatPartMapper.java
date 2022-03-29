package com.juphoon.rtc.datacenter.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdAgentOpStatPartPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>jrtc_acd_agentop_stat_part表的mapper类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Mapper
public interface AcdAgentOpStatPartMapper extends BaseMapper<AcdAgentOpStatPartPO>, AcdCommonMapper {

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(AcdAgentOpStatPartPO record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    AcdAgentOpStatPartPO selectByPrimaryKey(Long id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(AcdAgentOpStatPartPO record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(AcdAgentOpStatPartPO record);

    default AcdAgentOpStatPartPO selectByUniqueCondition(SFunction<AcdAgentOpStatPartPO, ?> column, String columnValue) {
        LambdaQueryWrapper<AcdAgentOpStatPartPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(column, columnValue);
        return this.selectOne(queryWrapper);
    }
}