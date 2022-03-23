package com.juphoon.rtc.datacenter.api;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/22 17:30
 */
public enum StatType {

    /**
     * 15分钟统计
     */
    STAT_15MIN((byte) 1, 15 * 60 * 1000),

    /**
     * 30分钟统计
     */
    STAT_30MIN((byte) 2, 30 * 60 * 1000),

    /**
     * 小时统计
     */
    STAT_HOUR((byte) 3, 60 * 60 * 1000),

    /**
     * 日统计
     */
    STAT_DAY((byte) 4, 24 * 60 * 60 * 1000);


    /**
     * 类型
     */
    private byte statType;

    /**
     * 间隔
     */
    private long interval;

    StatType(byte statType, long interval) {
        this.statType = statType;
        this.interval = interval;
    }

    /**
     * 获取间隔时长
     *
     * @return
     */
    public long getInterval() {
        return interval;
    }


    /**
     * 获取统计类型
     *
     * @return
     */
    public byte getStatType() {
        return statType;
    }
}
