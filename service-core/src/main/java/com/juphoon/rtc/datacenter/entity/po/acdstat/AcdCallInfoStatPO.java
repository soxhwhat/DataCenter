package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * 话务分时段父类
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
     * 用event初始化部分字段
     *
     * @param event
     */
    public void fromEvent(Event event) {
        int skillLength = 32;
        if (StringUtils.isEmpty(event.skill()) || event.skill().length() > skillLength) {
            throwException("skill[" + event.skill() + "] is empty or length > " + skillLength);
        }
        this.setDomainId(event.domainId());
        this.setAppId(event.appId());
        this.setEventType(event.eventType());
        this.setEventNum(event.eventNumber());
        this.setEndType(event.endType());
        this.setSkill(event.skill());
    }

}