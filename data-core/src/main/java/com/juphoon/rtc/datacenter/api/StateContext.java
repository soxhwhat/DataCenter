package com.juphoon.rtc.datacenter.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Getter
@Setter
public class StateContext extends BasicContext {
    /**
     * 内容 json
     */
    @NotNull
    private State state;

    @Override
    public EventType getEventType() {
        return state.getEventType();
    }
}
