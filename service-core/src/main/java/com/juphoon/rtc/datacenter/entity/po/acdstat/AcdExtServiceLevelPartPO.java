package com.juphoon.rtc.datacenter.entity.po.acdstat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 时间内接通量时段报表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdExtServiceLevelPartPO extends AcdExtServiceLevelPO {

    /**
    * 汇总类型(15分钟、1小时等)
    */
    private Byte statType;

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getStatType() +
                    "|" + getDomainId() + "|" + getAppId() +
                    "|" + getSkill() + "|" + getServiceLevel();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }
}