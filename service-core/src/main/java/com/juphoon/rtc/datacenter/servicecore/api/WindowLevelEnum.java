package com.juphoon.rtc.datacenter.servicecore.api;

/**
 * @Author: Jiahui.Huang
 * @Date: 2022/7/19 14:17
 * @Description:
 * 窗口大小
 *
 */
public enum WindowLevelEnum {
    /**
     * 1分钟窗口
     */
    WIN_MIN((byte) 1, 60000L),

    /**
     * 5分钟窗口
     */
    WIN_5MIN((byte) 2, 300000L),

    /**
     * 10分钟窗口
     */
    WIN_10MIN((byte) 3, 600000L),

    /**
     * 30分钟窗口
     */
    WIN_30MIN((byte) 4, 1800000L),

    /**
     * 1小时窗口
     */
    WIN_HOUR((byte) 5, 3600000L),

    /**
     * 一天窗口
     */
    WIN_DAY((byte) 6, 86400000L);

    /**
     * 时间内类型
     */
    private byte windowLevel;

    /**
     * 时间内
     */
    private long time;

    WindowLevelEnum(byte windowLevel, long time) {
        this.windowLevel = windowLevel;
        this.time = time;
    }

    /**
     * 获取时间内
     *
     * @return
     */
    public long getTime() {
        return time;
    }

    /**
     * 获取时间内类型
     *
     * @return
     */
    public byte getWindowLevel() {
        return windowLevel;
    }
}
