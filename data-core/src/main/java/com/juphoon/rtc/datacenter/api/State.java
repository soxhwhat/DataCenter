package com.juphoon.rtc.datacenter.api;

import com.juphoon.rtc.datacenter.exception.JrtcUnknownEventException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Setter
@Getter
@ToString
public class State {
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
    private final String uniqueId;

    /**
     * 事件类型
     */
    private final Integer type;

    /**
     * 事件编号
     */
    private final Integer state;

    /**
     * 其他参数
     */
    private final String params;

    /**
     * 构造器
     *
     * @return
     */
    public static State.Builder builder() {
        return new Builder();
    }

    public State(Integer domainId, Integer appId, String uniqueId, Integer type, Integer state, String params) {
        this.domainId = domainId;
        this.appId = appId;
        this.uniqueId = uniqueId;
        this.type = type;
        this.state = state;
        this.params = params;
    }

    public static class Builder {
        private Integer domainId;

        private Integer appId;

        private String uuid;

        private Integer type;

        private Integer state;

        private String params;

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

        public Builder state(final Integer state) {
            this.state = state;
            return this;
        }

        public Builder params(final String params) {
            this.params = params;
            return this;
        }

        public State build() {
            assert null != type : "type 不能为空";
            assert null != state : "state 不能为空";

            return new State(domainId, appId, uuid, type, state, params);
        }
    }

    /**
     * 生成事件类型
     *
     * @return
     */
    public EventType getEventType() {
        for (EventType et : EventType.values()) {
            if (getType().equals(et.getType()) && getState().equals(et.getNumber())) {
                return et;
            }
        }

        throw new JrtcUnknownEventException(getType() + ":" + getState());
    }
}
