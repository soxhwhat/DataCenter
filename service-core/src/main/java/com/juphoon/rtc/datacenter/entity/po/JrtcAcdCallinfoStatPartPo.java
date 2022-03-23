package com.juphoon.rtc.datacenter.entity.po;

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
@TableName("jrtc_acd_callinfo_stat_part")
public class JrtcAcdCallinfoStatPartPo extends JrtcAcdCommonPo {

    /**
    * 汇总类型(1-15分钟、2-小时)
    */
    private Byte statType;

    /**
    * 技能组
    */
    private String skill;

    /**
    * 事件类型
    */
    private Short eventType;

    /**
    * 事件编号
    */
    private Short eventNum;

    /**
    * 事件结束类型
    */
    private Short endType;

}