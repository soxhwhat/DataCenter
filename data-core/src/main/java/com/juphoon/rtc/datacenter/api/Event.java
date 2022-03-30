package com.juphoon.rtc.datacenter.api;

import com.juphoon.rtc.datacenter.exception.JrtcUnknownEventException;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
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
@ToString
public class Event {
    private final Integer domainId;

    private final Integer appId;

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
     * 其他参数
     */
    private final Map<String, String> params;

    public static Event.Builder builder() {
        return new Builder();
    }

    public Event(Integer domainId, Integer appId, String uuid, Integer type, Integer number, Map<String, String> params) {
        this.domainId = domainId;
        this.appId = appId;
        this.uuid = uuid;
        this.type = type;
        this.number = number;
        this.params = params;
    }

    public static class Builder {
        private Integer domainId;

        private Integer appId;

        private String uuid;

        private Integer type;

        private Integer number;

        private Map<String, String> params;

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

        public Builder params(final Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Event build() {
//            assert null != domainId : "domainId 不能为空";
//            assert null != appId : "appId 不能为空";
            assert null != type : "eventType 不能为空";
            assert null != number : "eventNumber 不能为空";

            return new Event(domainId, appId, uuid, type, number, params);
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

    public Map<String, String> getParams() {
        return params;
    }

    /**
     * 深度复制map
     *
     * @return
     */
    public Map<String, String> getParamsCopy() {
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
    public long beginTimestamp() {
        return Long.parseLong(params.get("beginTimestamp"));
    }

    /**
     * 事件结束时间
     *
     * @return
     */
    public long endTimestamp() {
        return Long.parseLong(params.get("endTimestamp"));
    }


    public int domainId() {
        return domainId;
    }

    public int appId() {
        return appId;
    }

    public long duration() {
        return Long.parseLong(params.getOrDefault("duration", "0"));
    }

    public Integer endType() {
        return Integer.parseInt(params.getOrDefault("endType", "0"));
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
        return Integer.parseInt(params.getOrDefault("extStatus", "0"));
    }

//    private static String defaultShirt() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        return sdf.format(new Date());
//    }

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
