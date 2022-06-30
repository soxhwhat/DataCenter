package com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 时间内接通量日报表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdExtServiceLevelDailyPO extends AcdExtServiceLevelPO {

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getDomainId() +
                    "|" + getAppId() + "|" + getSkill() +
                    "|" + getServiceLevel();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }

}