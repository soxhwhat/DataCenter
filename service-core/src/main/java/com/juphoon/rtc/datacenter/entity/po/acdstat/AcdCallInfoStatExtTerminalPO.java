package com.juphoon.rtc.datacenter.entity.po.acdstat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class AcdCallInfoStatExtTerminalPO extends AcdCommonPO {

    /**
     * 技能组
     */
    private String skill;

    /**
     * 终端类型(H5/小程序/安卓)
     */
    private Byte terminal;
}