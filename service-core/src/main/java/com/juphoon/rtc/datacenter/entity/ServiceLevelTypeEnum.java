package com.juphoon.rtc.datacenter.entity;

/**
 * <p>服务水平枚举类</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/4/6
 */
public enum ServiceLevelTypeEnum {

    /**
     * 10s内
     */
    SERVICE_LEVEL_10SEC((byte) 1, 10 * 1000),

    /**
     * 20s内
     */
    SERVICE_LEVEL_20SEC((byte) 2, 20 * 1000),

    /**
     * 30s内
     */
    SERVICE_LEVEL_30SEC((byte) 3, 30 * 1000),

    /**
     * 40s内
     */
    SERVICE_LEVEL_40SEC((byte) 4, 40 * 1000),

    /**
     * 50s内
     */
    SERVICE_LEVEL_50SEC((byte) 5, 50 * 1000),

    /**
     * 60s内
     */
    SERVICE_LEVEL_60SEC((byte) 6, 60 * 1000);


    /**
     * 时间内类型
     */
    private byte serviceLevel;

    /**
     * 时间内
     */
    private long inTime;

    ServiceLevelTypeEnum(byte serviceLevel, long inTime) {
        this.serviceLevel = serviceLevel;
        this.inTime = inTime;
    }

    /**
     * 获取时间内
     *
     * @return
     */
    public long getInTime() {
        return inTime;
    }

    /**
     * 获取时间内类型
     *
     * @return
     */
    public byte getServiceLevel() {
        return serviceLevel;
    }
}
