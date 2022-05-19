package com.juphoon.rtc.datacenter.api;

/**
 * <p>消息队列类型</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/18/22 5:16 PM
 */
public enum EventStorageType {
    /**
     * NONE 空实现
     */
    NONE("none", "空实现"),

    /**
     * sqlite 实现
     */
    SQLITE("sqlite", "sqlite 实现");

    /**
     * 类型
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    EventStorageType(String name, String description) {
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
