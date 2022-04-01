package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.utils.Md5Util;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 话务分时段汇总表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdCallInfoStatPartPO extends AcdCallInfoStatPO {
    /**
     * 汇总类型(1-15分钟、2-小时)
     */
    private Byte statType;

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getStatType() +
                    "|" + getSkill() + "|" + getEventType() +
                    "|" + getEventNum() + "|" + getEndType() +
                    "|" + getDomainId() + "|" + getAppId();
            super.setUniqueKey(DigestUtils.md5Hex(str));

        }
        return super.getUniqueKey();
    }
}