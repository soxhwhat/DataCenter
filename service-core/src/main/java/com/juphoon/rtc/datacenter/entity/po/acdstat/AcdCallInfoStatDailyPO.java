package com.juphoon.rtc.datacenter.entity.po.acdstat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 话务运营日报表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdCallInfoStatDailyPO extends AcdCallInfoStatPO {

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getSkill() +
                    "|" + getEventType() + "|" + getEventNum() +
                    "|" + getEndType() + "|" + getDomainId() +
                    "|" + getAppId();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }
}