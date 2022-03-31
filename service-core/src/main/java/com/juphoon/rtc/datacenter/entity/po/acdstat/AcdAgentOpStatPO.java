package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * 坐席操作父类
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
     * 扩展状态(示忙子状态小休等)
     */
    private Integer extStatus;

    /**
     * 用event初始化部分字段
     *
     * @param event  event
     */
    public void fromEvent(Event event) {
        int length = 32;
        if (event.extStatus() != null && event.extStatus() < 0) {
            throwException("extStatus[" + event.extStatus() + "] < 0");
        }
        if (StringUtils.isEmpty(event.agentId()) || event.agentId().length() > (length+length)) {
            throwException("agentId[" + event.agentId() + "] is empty or length > " + (length+length));
        }
        if (StringUtils.hasText(event.team()) && event.team().length() > length) {
            throwException("team[" + event.team() + "].length > " + length);
        }
        if (StringUtils.isEmpty(event.shift()) || event.shift().length() > length) {
            throwException("shift[" + event.shift() + "] is empty or length > " + length);
        }
        if (StringUtils.hasText(event.skill()) && event.skill().length() > length) {
            throwException("skill[" + event.skill() + "].length > " + length);
        }
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