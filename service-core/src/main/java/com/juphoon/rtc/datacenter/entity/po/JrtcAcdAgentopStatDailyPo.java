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
@TableName("jrtc_acd_agentop_stat_daily")
public class JrtcAcdAgentopStatDailyPo extends JrtcAcdCommonPo {

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