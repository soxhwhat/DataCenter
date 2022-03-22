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
public class StatType {
    long statType;
    long interval;

    public StatType(long statType, long interval) {
        this.statType = statType;
        this.interval = interval;
    }

    public StatType() {
    }

    public long getStatType() {
        return statType;
    }

    public void setStatType(long statType) {
        this.statType = statType;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
