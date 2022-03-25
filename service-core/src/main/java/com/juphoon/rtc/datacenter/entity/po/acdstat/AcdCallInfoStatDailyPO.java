package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("jrtc_acd_callinfo_stat_daily")
public class AcdCallInfoStatDailyPO extends AcdCallInfoStatPO {

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = new StringBuilder(getStatTime() + "|").append(getSkill())
                    .append("|").append(getEventType()).append("|").append(getEventNum())
                    .append("|").append(getEndType()).append("|").append(getDomainId())
                    .append("|").append(getAppId()).toString();
            super.setUniqueKey(Md5Util.encryptMd5(str));
        }
        return super.getUniqueKey();
    }
}