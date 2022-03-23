package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 话务运营日报表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
@TableName("jrtc_acd_callinfo_stat_ext_terminal_daily")
public class AcdCallInfoStatExtTerminalDailyPO extends AcdCommonPO {

    /**
    * 技能组
    */
    private String skill;

    /**
    * 终端类型(H5/小程序/安卓)
    */
    private Byte terminal;
}