package com.juphoon.rtc.datacenter.api;

/**
 * <p>消息队列类型</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/18/22 5:16 PM
 */
public enum EventQueueType {
    /**
     * NONE 空实现
     */
    NONE("none", "空实现"),

    /**
     * disruptor 实现
     */
    DISRUPTOR("disruptor", "disruptor 实现");

    /**
     * 类型
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    EventQueueType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
