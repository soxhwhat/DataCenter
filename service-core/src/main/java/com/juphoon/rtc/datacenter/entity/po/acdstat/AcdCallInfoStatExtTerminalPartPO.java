package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yuan
 */
@Getter
@Setter
@ToString
@TableName("jrtc_acd_callinfo_stat_ext_terminal_part")
public class AcdCallInfoStatExtTerminalPartPO extends AcdCommonPO {

    /**
    * 汇总类型(15分钟、1小时等)
    */
    private Byte statType;

    /**
    * 技能组
    */
    private String skill;

    /**
    * 终端类型(H5/小程序/安卓)
    */
    private Byte terminal;
}