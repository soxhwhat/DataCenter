package com.juphoon.rtc.datacenter.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juphoon.rtc.datacenter.exception.JrtcUnknownEventException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Setter
@Getter
@ToString
public class Event {
    /**
     * 域ID
     */
    private final Integer domainId;

    /**
     * 应用ID
     */
    private final Integer appId;

    /**
     * 事件唯一ID
     */
    private final String uuid;

    /**
     * 事件类型
     */
    private final Integer type;

    /**
     * 事件编号
     */
    private final Integer number;

    /**
     * 时间戳
     */
    private final Long timestamp;

    /**
     * 更新时间
     */
//    private final Long updateTime = System.currentTimeMillis();

    /**
     * 其他参数
     */
    private final Map<String, Object> params;

    /**
     * 构造器
     *
     * @return
     */
    public static Event.Builder builder() {
        return new Builder();
    }

    public Event(Integer domainId, Integer appId, String uuid, Integer type, Integer number, Long timestamp, Map<String, Object> params) {
        this.domainId = domainId;
        this.appId = appId;
        this.uuid = uuid;
        this.type = type;
        this.number = number;
        this.params = params;
        this.timestamp = timestamp;
    }

    public static class Builder {
        private Integer domainId;

        private Integer appId;

        private String uuid;

        private Integer type;

        private Integer number;

        private Long timestamp;

        private Map<String, Object> params;

        Builder() {
            domainId = -1;
            appId = -1;
            this.uuid = UUID.randomUUID().toString();
        }

        public Builder domainId(final Integer domainId) {
            this.domainId = domainId;
            return this;
        }

        public Builder appId(final Integer appId) {
            this.appId = appId;
            return this;
        }

        public Builder uuid(final String uuid) {
            if (StringUtils.isEmpty(uuid)) {
                this.uuid = UUID.randomUUID().toString();
            } else {
                this.uuid = uuid;
            }
            return this;
        }

        public Builder type(final Integer type) {
            this.type = type;
            return this;
        }

        public Builder number(final Integer number) {
            this.number = number;
            return this;
        }

        public Builder timestamp(final Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder params(final Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public Event build() {
//            assert null != domainId : "domainId 不能为空";
//            assert null != appId : "appId 不能为空";
            assert null != type : "eventType 不能为空";
            assert null != number : "eventNumber 不能为空";

            return new Event(domainId, appId, uuid, type, number, timestamp, params);
        }
    }

    //************************ 方法区 **************************
    // TODO 各自业务的方法用各自业务的wrapper封装


    public String getUuid() {
        return uuid;
    }

    /**
     * 生成事件类型
     *
     * @return
     */
    public EventType getEventType() {
        for (EventType et : EventType.values()) {
            if (eventType().equals(et.getType()) && eventNumber().equals(et.getNumber())) {
                return et;
            }
        }

        throw new JrtcUnknownEventException(eventType() + ":" + eventNumber());
    }

    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * 深度复制map
     *
     * @return
     */
    @JsonIgnore
    public Map<String, Object> getParamsCopy() {
        if (null == params) {
            return new HashMap<>(0);
        }
        return new HashMap<>(params);
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
    public Long beginTimestamp() {
        return ((Number) params.getOrDefault("beginTimestamp",0L)).longValue();
    }

    public Long timestamp() {
        return timestamp;
    }

    /**
     * 事件结束时间
     *
     * @return
     */
    public long endTimestamp() {
        return ((Number) params.getOrDefault("endTimestamp",0L)).longValue();
    }

    /**
     * 排队开始时间，用于求服务水平
     *
     * @return
     */
    public long incomingTimestamp() {
        return (long) params.getOrDefault("incomingTimestamp", 0L);
    }

    public int domainId() {
        return domainId;
    }

    public int appId() {
        return appId;
    }

    public long duration() {
        return (long) params.getOrDefault("duration", 0L);
    }

    public int endType() {
        return (int) params.getOrDefault("endType", 0);
    }

    public boolean isEndWithException() {
        return endType() == 0 ? false : true;
    }

    /**
     * 排队机带过来的是queue
     *
     * @return
     */
    public String skill() {
        return (String) params.getOrDefault("queue", "");
    }

    public String channel() {
        return (String) params.getOrDefault("channel", "");
    }

    public String platform() {
        return (String) params.getOrDefault("platform", "");
    }

    public String team() {
        return (String) params.getOrDefault("team", "");
    }

    public String shift() {
        return (String) (StringUtils.isEmpty(params.get("shift")) ? defaultShirt() : params.get("shift"));
    }

    public String agentId() {
        return (String) params.getOrDefault("agentId", "");
    }

    public int subState() {
        return (int) params.getOrDefault("subState", 0);
    }

    private static String defaultShirt() {
        return DateFormatUtils.format(new Date(System.currentTimeMillis()), "yyyyMMdd");
    }

    public static void main(String[] args) {
//        System.out.println(defaultShirt());

        String ret = defaultShirt();
        System.out.println(ret);

//        long begin = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            defaultShirt();
//        }
//        System.out.println("defaultShirt() cost:" + (System.currentTimeMillis() - begin));
//
//        begin = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            defaultShirt2();
//        }
//        System.out.println("defaultShirt2() cost:" + (System.currentTimeMillis() - begin));
//
//        begin = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            defaultShirt();
//        }
//        System.out.println("defaultShirt() cost:" + (System.currentTimeMillis() - begin));
//
//        begin = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            defaultShirt2();
//        }
//        System.out.println("defaultShirt2() cost:" + (System.currentTimeMillis() - begin));
        // TODO Auto-generated method stub
        System.out.println(System.getProperty("user.dir"));
    }
}
