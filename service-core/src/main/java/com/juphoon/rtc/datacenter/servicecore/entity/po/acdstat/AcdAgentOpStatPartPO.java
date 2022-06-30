package com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 坐席时段报表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdAgentOpStatPartPO extends AcdAgentOpStatPO {

    /**
     * 汇总类型(15分钟、1小时等)
     */
    private Byte statType;

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getStatType() +
                    "|" + getAgentId() + "|" + getEventType() +
                    "|" + getEventNum() + "|" + getEndType() +
                    "|" + getDomainId() + "|" + getAppId() +
                    "|" + getShift() + "|" + getTeam() +
                    "|" + getSkill() + "|" + getSubState();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }
}