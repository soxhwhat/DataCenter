package com.juphoon.rtc.datacenter.api;

import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Setter
@ToString
public class Event {
    private Integer domainId;

    private Integer appId;

    private String uuid;

    /**
     * 事件类型
     */
    private Integer type;

    /**
     * 事件编号
     */
    private Integer number;

    /**
     * 其他参数
     */
    private Map<String, String> params;


    //************************ 方法区 **************************


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 生成事件类型
     *
     * @return
     */
    public EventType getEventType() {
        return new EventType(type, number);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public short eventType() {
        return type.shortValue();
    }

    public short eventNumber() {
        return number.shortValue();
    }

    /**
     * 事件开始时间
     *
     * @return
     */
    public long beginTimestamp() {
        return Long.valueOf(params.get("beginTimestamp"));
    }

    /**
     * 事件结束时间
     *
     * @return
     */
    public long endTimestamp() {
        return Long.valueOf(params.get("endTimestamp"));
    }


    public int domainId() {
        return domainId;
    }

    public int appId() {
        return appId;
    }

    public long duration() {
        return Long.valueOf(params.get("duration"));
    }

    public short endType() {
        return Short.valueOf(params.get("endType"));
    }

    public String skill() {
        return params.get("queue");
    }
    public String team() {
        return params.get("team");
    }
    public String shift() {
        return params.get("shift");
    }
    public String agentId() {
        return params.get("agentId");
    }
    public Short extStatus() {
        return params.get("extStatus") == null ? null : Short.valueOf(params.get("extStatus"));
    }
}
