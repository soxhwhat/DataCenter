package com.juphoon.rtc.datacenter.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdCallInfoStatDailyPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>jrtc_acd_callinfo_stat_daily表的mapper类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Mapper
@DS("kiwi")
public interface AcdCallInfoStatDailyMapper extends BaseMapper<AcdCallInfoStatDailyPO>, AcdCommonMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(AcdCallInfoStatDailyPO record);

    AcdCallInfoStatDailyPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AcdCallInfoStatDailyPO record);

    int updateByPrimaryKey(AcdCallInfoStatDailyPO record);

    default AcdCallInfoStatDailyPO selectByCondition(SFunction<AcdCallInfoStatDailyPO, ?> column, Integer columnValue) {
        LambdaQueryWrapper<AcdCallInfoStatDailyPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(column, columnValue);
        return this.selectOne(queryWrapper);
    }

}