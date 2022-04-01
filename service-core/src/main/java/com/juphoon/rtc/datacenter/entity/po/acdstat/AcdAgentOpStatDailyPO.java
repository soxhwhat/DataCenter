package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.utils.Md5Util;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 坐席操作日汇总表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdAgentOpStatDailyPO extends AcdAgentOpStatPO {

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getAgentId() +
                    "|" + getEventType() + "|" + getEventNum() +
                    "|" + getEndType() + "|" + getDomainId() +
                    "|" + getAppId() + "|" + getShift() +
                    "|" + getTeam() + "|" + getSkill() +
                    "|" + getExtStatus();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }

}