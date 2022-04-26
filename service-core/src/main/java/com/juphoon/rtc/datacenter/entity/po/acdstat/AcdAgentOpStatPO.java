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
    private Integer subState;

    /**
     * 用event初始化部分字段
     *
     * @param event  event
     */
    public void fromEvent(Event event) {
        commonCheckParam(event);
        this.setDomainId(event.domainId());
        this.setAppId(event.appId());
        this.setEventType(event.eventType());
        this.setEventNum(event.eventNumber());
        this.setEndType(event.endType());
        this.setAgentId(cutAgentId(event.agentId()));
        this.setSubState(event.subState());
        this.setTeam(event.team());
        this.setShift(event.shift());
        this.setSkill(event.skill());
    }

    protected String cutAgentId(String agentId) {
        String preStr = "[username:";
        String splitStr = "@";
        return agentId.split(splitStr)[0].replace(preStr,"");
    }

    protected void commonCheckParam(Event event) {
        int length = 32;
        if (event.subState() < 0) {
            throwException("subState[" + event.subState() + "] < 0");
        }
        if (StringUtils.hasText(event.skill()) && event.skill().length() > length) {
            throwException("skill[" + event.skill() + "].length > " + length);
        }
        if (StringUtils.isEmpty(event.agentId()) || event.agentId().length() > (length + length)) {
            throwException("agentId[" + event.agentId() + "] is empty or length > " + (length + length));
        }
        if (StringUtils.hasText(event.team()) && event.team().length() > length) {
            throwException("team[" + event.team() + "].length > " + length);
        }
        if (StringUtils.isEmpty(event.shift()) || event.shift().length() > length) {
            throwException("shift[" + event.shift() + "] is empty or length > " + length);
        }
    }

}