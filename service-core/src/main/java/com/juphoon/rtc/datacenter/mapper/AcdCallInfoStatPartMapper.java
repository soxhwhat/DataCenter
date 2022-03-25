package com.juphoon.rtc.datacenter.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdCallInfoStatPartPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>jrtc_acd_callinfo_stat_part表的mapper类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Mapper
@DS("mysql")
public interface AcdCallInfoStatPartMapper extends BaseMapper<AcdCallInfoStatPartPO>, AcdCommonMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(AcdCallInfoStatPartPO record);

    AcdCallInfoStatPartPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AcdCallInfoStatPartPO record);

    int updateByPrimaryKey(AcdCallInfoStatPartPO record);

    default AcdCallInfoStatPartPO selectByUniqueCondition(SFunction<AcdCallInfoStatPartPO, ?> column, String columnValue) {
        LambdaQueryWrapper<AcdCallInfoStatPartPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(column, columnValue);
        return this.selectOne(queryWrapper);
    }
}