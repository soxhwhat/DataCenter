package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("jrtc_acd_agentop_stat_daily")
public class AcdAgentOpStatDailyPO extends AcdAgentOpStatPO {

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = new StringBuilder(getStatTime() + "|").append(getAgentId())
                    .append("|").append(getEventType()).append("|").append(getEventNum())
                    .append("|").append(getEndType()).append("|").append(getDomainId())
                    .append("|").append(getAppId()).append("|").append(getShift())
                    .append("|").append(getTeam()).append("|").append(getSkill())
                    .append("|").append(getExtStatus())
                    .toString();
            super.setUniqueKey(Md5Util.encryptMd5(str));
        }
        return super.getUniqueKey();
    }

}