package cloud.juphoon.jrtc.mapper;

import cloud.juphoon.jrtc.entity.po.JrtcAcdCallinfoStatDailyPo;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface JrtcAcdCallinfoStatDailyMapper extends BaseMapper<JrtcAcdCallinfoStatDailyPo>,JrtcAcdCommonMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(JrtcAcdCallinfoStatDailyPo record);

    JrtcAcdCallinfoStatDailyPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JrtcAcdCallinfoStatDailyPo record);

    int updateByPrimaryKey(JrtcAcdCallinfoStatDailyPo record);

    default JrtcAcdCallinfoStatDailyPo selectByCondition(SFunction<JrtcAcdCallinfoStatDailyPo, ?> column, Integer columnValue) {
        LambdaQueryWrapper<JrtcAcdCallinfoStatDailyPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(column, columnValue);
        return this.selectOne(queryWrapper);
    }

}