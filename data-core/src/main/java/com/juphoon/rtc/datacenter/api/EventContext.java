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
public class EventContext extends BaseContext {
    public EventContext(@NotNull Event event) {
        this.event = event;
    }

    /**
     * 消息体
     */
    @NotNull
    private final Event event;

    @Override
    public EventType getEventType() {
        return event.getEventType();
    }
}
