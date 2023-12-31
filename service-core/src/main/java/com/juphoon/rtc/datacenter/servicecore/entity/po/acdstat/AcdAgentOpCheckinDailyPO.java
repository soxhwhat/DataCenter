package com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 坐席签入签出日表
 * 注意：父类的一些字段不需要，实际字段根据表的字段来
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdAgentOpCheckinDailyPO extends AcdAgentOpStatPO {

    /**
     * 首次签入时间戳(毫秒)
     */
    private Long firstCheckin;

    /**
     * 最后签入时间戳(毫秒)
     */
    private Long lastCheckin;

    /**
     * 最后签出时间戳(毫秒)
     */
    private Long lastCheckout;

    /**
     * 用event初始化部分字段
     *
     * @param event  event
     */
    @Override
    public void fromEvent(Event event) {
        commonCheckParam(event);
        this.setDomainId(event.domainId());
        this.setAppId(event.appId());
        this.setAgentId(cutAgentId(event.agentId()));
        this.setTeam(event.team());
        this.setShift(event.shift());
    }

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getAgentId() +
                    "|" + getDomainId() + "|" + getAppId() +
                    "|" + getShift() + "|" + getTeam();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }
}