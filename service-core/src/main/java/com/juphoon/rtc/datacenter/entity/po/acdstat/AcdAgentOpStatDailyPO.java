package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.juphoon.rtc.datacenter.utils.Md5Util;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
        // TODO:MD5 apache-
        if (null == super.getUniqueKey()) {
            String str = getStatTime() + "|" + getAgentId() +
                    "|" + getEventType() + "|" + getEventNum() +
                    "|" + getEndType() + "|" + getDomainId() +
                    "|" + getAppId() + "|" + getShift() +
                    "|" + getTeam() + "|" + getSkill() +
                    "|" + getExtStatus();
            super.setUniqueKey(Md5Util.encryptMd5(str));
        }
        return super.getUniqueKey();
    }

}