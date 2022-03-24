package com.juphoon.rtc.datacenter.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Setter
@ToString
public class Event {
    /**
     * 消息体
     */
    private Integer type;

    private Integer number;

    private Map<String, Object> params;

    public EventType getEventType() {
        return new EventType(type, number);
    }

    public Map<String, Object> getParams() {
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
        return (Long) params.get("beginTimestamp");
    }

    /**
     * 事件结束时间
     *
     * @return
     */
    public long endTimestamp() {
        return (Long) params.get("endTimestamp");
    }


    public int domainId() {
        return (int) params.get("domainId");
    }

    public int appId() {
        return (int) params.get("appId");
    }

    public long duration() {
        return (long) params.get("duration");
    }

    public short endType() {
        return (short) params.get("endType");
    }

    public short endNum() {
        return (short) params.get("eventNum");
    }

//    public short eventType() {
//        return (short) params.get("eventType");
//    }

    public String skill() {
        return (String) params.get("skill");
    }

}
