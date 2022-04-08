package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
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
public class AcdAgentOpCheckDailyPO extends AcdAgentOpStatPO {

    /**
     * 首次签入时间戳(毫秒)
     */
    private Long firstCheckIn;

    /**
     * 最后签入时间戳(毫秒)
     */
    private Long lastCheckIn;

    /**
     * 最后签出时间戳(毫秒)
     */
    private Long lastCheckOut;

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
        this.setAgentId(event.agentId());
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