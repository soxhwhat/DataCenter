package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.utils.Md5Util;
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
                    "|" + getSkill() + "|" + getExtStatus();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }
}