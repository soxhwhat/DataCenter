package com.juphoon.rtc.datacenter.entity.po.acdstat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 话务渠道时段报表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdExtTerminalPartPO extends AcdExtTerminalPO {

    /**
     * 汇总类型(15分钟、1小时等)
     */
    private Byte statType;

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getStatType() +
                    "|" + getDomainId() + "|" + getAppId() +
                    "|" + getChannel() + "|" + getPlatform() +
                    "|" + getSkill();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }
}