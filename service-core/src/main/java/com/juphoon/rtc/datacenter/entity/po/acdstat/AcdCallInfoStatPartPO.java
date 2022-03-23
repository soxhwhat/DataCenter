package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 话务分时段汇总表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
@TableName("jrtc_acd_callinfo_stat_part")
public class AcdCallInfoStatPartPO extends AcdCallInfoStatPO {
    /**
    * 汇总类型(1-15分钟、2-小时)
    */
    private Byte statType;
}