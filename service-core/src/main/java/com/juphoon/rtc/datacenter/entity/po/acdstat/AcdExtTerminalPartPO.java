package com.juphoon.rtc.datacenter.entity.po.acdstat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 话务渠道时段报表
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

}