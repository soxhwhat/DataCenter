package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.baomidou.mybatisplus.annotation.TableName;
import com.juphoon.rtc.datacenter.utils.Md5Util;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 坐席时段报表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
@TableName("jrtc_acd_agentop_stat_part")
public class AcdAgentOpStatPartPO extends AcdAgentOpStatPO {

    /**
     * 汇总类型(15分钟、1小时等)
     */
    private Byte statType;

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = new StringBuilder(getStatTime() + "|").append(getStatType())
                    .append("|").append(getAgentId()).append("|").append(getEventType())
                    .append("|").append(getEventNum()).append("|").append(getEndType())
                    .append("|").append(getDomainId()).append("|").append(getAppId())
                    .append("|").append(getShift()).append("|").append(getTeam())
                    .append("|").append(getSkill()).append("|").append(getExtStatus())
                    .toString();
            super.setUniqueKey(Md5Util.encryptMd5(str));
        }
        return super.getUniqueKey();
    }
}