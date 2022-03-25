package com.juphoon.rtc.datacenter.api;

import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public Integer eventType() {
        return type;
    }

    public Integer eventNumber() {
        return number;
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
        return Long.valueOf(params.getOrDefault("duration", "0"));
    }

    public Integer endType() {
        return Integer.valueOf(params.getOrDefault("endType", "0"));
    }

    /**
     * 排队机带过来的是queue
     *
     * @return
     */
    public String skill() {
        return params.getOrDefault("queue", "");
    }

    public String team() {
        return params.getOrDefault("team", "");
    }

    public String shift() {
        return params.getOrDefault("shift", defaultShirt());
    }

    public String agentId() {
        return params.getOrDefault("agentId", "");
    }

    public Integer extStatus() {
        return Integer.valueOf(params.getOrDefault("extStatus", "0"));
    }

    private static String defaultShirt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(defaultShirt());
    }
}
