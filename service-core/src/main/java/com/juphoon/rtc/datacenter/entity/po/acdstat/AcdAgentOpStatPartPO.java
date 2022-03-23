package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* 坐席时段报表
 *
* @author Yuan
*/
@Getter
@Setter
@ToString
@TableName("jrtc_acd_agentop_stat_part")
public class AcdAgentOpStatPartPO extends AcdCommonPO {

    /**
    * 汇总类型(15分钟、1小时等)
    */
    private Byte statType;

    /**
    * 事件触发成员
    */
    private String agentId;

    /**
    * 班次(默认为日期)
    */
    private String shift;

    /**
    * 班组(可选)
    */
    private String team;

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

    /**
    * 扩展状态(示忙子状态小休等)
    */
    private Short extStatus;
}