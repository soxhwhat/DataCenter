package com.juphoon.rtc.datacenter.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Getter
@Setter
public class StateContext extends BasicContext {
    public StateContext() {
    }

    public StateContext(EventType eventType, String params) {
        this.eventType = eventType;
        this.params = params;
    }

    private EventType eventType;

    /**
     * 内容 json
     */
    @NotNull
    private String params;

    @Override
    public EventType getEventType() {
        return eventType;
    }
}
