package com.juphoon.rtc.datacenter.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdAgentOpStatDailyPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>jrtc_acd_agentop_stat_daily表的mapper类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Mapper
public interface AcdAgentOpStatDailyMapper extends BaseMapper<AcdAgentOpStatDailyPO>, AcdCommonMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(AcdAgentOpStatDailyPO record);

    AcdAgentOpStatDailyPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AcdAgentOpStatDailyPO record);

    int updateByPrimaryKey(AcdAgentOpStatDailyPO record);

    default AcdAgentOpStatDailyPO selectByCondition(SFunction<AcdAgentOpStatDailyPO, ?> column, String columnValue) {
        LambdaQueryWrapper<AcdAgentOpStatDailyPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(column, columnValue);
        return this.selectOne(queryWrapper);
    }
}