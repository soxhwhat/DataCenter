package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 坐席操作日汇总表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdAgentOpStatPO extends AcdCommonPO {

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

    /**
     * 用event初始化部分字段
     *
     * @param event
     */
    public void fromEvent(Event event) {
        this.setDomainId(event.domainId());
        this.setAppId(event.appId());
        this.setEventType(event.eventType());
        this.setEventNum(event.eventNumber());
        this.setEndType(event.endType());
        this.setAgentId(event.agentId());
        this.setExtStatus(event.extStatus());
        this.setTeam(event.team());
        this.setShift(event.shift());
        this.setSkill(event.skill());
    }

}