package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * 时间内接通量日报表
 * 注意：父类的一些字段不需要，实际字段根据表的字段来
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdExtServiceLevelPO extends AcdCommonPO {

    /**
    * 技能组
    */
    private String skill;

    /**
    * 20s,30s等时间
    */
    private Byte serviceLevel;

    /**
     * 用event初始化部分字段
     *
     * @param event  event
     */
    public void fromEvent(Event event) {
        int skillLength = 32;
        if (StringUtils.isEmpty(event.skill()) || event.skill().length() > skillLength) {
            throwException("skill[" + event.skill() + "] is empty or length > " + skillLength);
        }
        this.setDomainId(event.domainId());
        this.setAppId(event.appId());
        this.setSkill(event.skill());
    }

}