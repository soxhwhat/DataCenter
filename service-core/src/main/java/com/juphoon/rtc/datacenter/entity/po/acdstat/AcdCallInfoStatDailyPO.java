package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.utils.Md5Util;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
        // TODO:MD5 apache-
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getSkill() +
                    "|" + getEventType() + "|" + getEventNum() +
                    "|" + getEndType() + "|" + getDomainId() +
                    "|" + getAppId();
            super.setUniqueKey(Md5Util.encryptMd5(str));
        }
        return super.getUniqueKey();
    }
}