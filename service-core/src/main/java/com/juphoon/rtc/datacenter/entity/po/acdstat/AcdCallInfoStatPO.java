package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 话务分时段汇总表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdCallInfoStatPO extends AcdCommonPO {
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
     * 用event初始化部分字段
     *
     * @param event
     */
    public void fromEvent(Event event) {
        this.setDomainId(event.appId());
        this.setAppId(event.domainId());
        this.setSkill(event.skill());
        this.setEventType(event.eventType());
        this.setEventNum(event.eventNumber());
        this.setDuration(event.duration());
        this.setEndType(event.endType());
    }
}