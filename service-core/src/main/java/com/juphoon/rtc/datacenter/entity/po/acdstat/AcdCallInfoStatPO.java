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
    private Integer eventType;

    /**
     * 事件编号
     */
    private Integer eventNum;

    /**
     * 事件结束类型
     */
    private Integer endType;

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
        this.setSkill(event.skill());
    }


}