package com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 话务渠道日报表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdExtTerminalDailyPO extends AcdExtTerminalPO {

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getDomainId() +
                    "|" + getAppId() + "|" + getChannel() +
                    "|" + getPlatform() + "|" + getSkill();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }
}