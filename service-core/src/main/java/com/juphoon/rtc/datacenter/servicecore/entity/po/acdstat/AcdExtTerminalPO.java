package com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * 话务渠道父类
 * <p>
 * 注意：父类的一些字段不需要，实际字段根据表的字段来
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdExtTerminalPO extends AcdCommonPO {

    /**
     * 技能组
     */
    private String skill;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 平台-终端类型(H5/小程序/安卓)
     */
    private String platform;

    /**
     * 用event初始化部分字段
     *
     * @param event event
     */
    public void fromEvent(Event event) {
        int length = 32;
        if (StringUtils.isEmpty(event.skill()) || event.skill().length() > length) {
            throwException("skill[" + event.skill() + "] is empty or length > " + length);
        }
        if (StringUtils.hasText(event.channel()) && event.channel().length() > length) {
            throwException("channel[" + event.channel() + "] is empty or length > " + length);
        }
        if (StringUtils.hasText(event.platform()) && event.platform().length() > length) {
            throwException("platform[" + event.platform() + "] is empty or length > " + length);
        }
        this.setDomainId(event.domainId());
        this.setAppId(event.appId());
        this.setSkill(event.skill());
        this.setChannel(event.channel());
        this.setPlatform(event.platform());
    }
}