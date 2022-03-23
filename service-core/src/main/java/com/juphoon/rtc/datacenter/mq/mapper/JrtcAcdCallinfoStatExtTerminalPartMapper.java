package com.juphoon.rtc.datacenter.mq.mapper;

import com.juphoon.rtc.datacenter.entity.po.JrtcAcdCallinfoStatExtTerminalPartPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * <p>jrtc_acd_callinfo_stat_ext_terminal_part表的mapper类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Mapper
public interface JrtcAcdCallinfoStatExtTerminalPartMapper extends BaseMapper<JrtcAcdCallinfoStatExtTerminalPartPo> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(JrtcAcdCallinfoStatExtTerminalPartPo record);

    JrtcAcdCallinfoStatExtTerminalPartPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JrtcAcdCallinfoStatExtTerminalPartPo record);

    int updateByPrimaryKey(JrtcAcdCallinfoStatExtTerminalPartPo record);
}