package com.juphoon.rtc.datacenter.datacore.api;

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
public class StateContext extends BaseContext {
    public StateContext(@NotNull State state) {
        this.state = state;
    }

    /**
     * 内容 json
     */
    @NotNull
    private final State state;

    @Override
    public EventType getEventType() {
        return state.getEventType();
    }
}
