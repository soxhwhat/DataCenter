package com.juphoon.rtc.datacenter.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Getter
@Setter
public class LogContext extends BasicContext {
    /**
     * 内容
     */
    @NotNull
    private List<String> logs;

    @Override
    public EventType getEventType() {
        return EventType.LOG_EVENT;
    }
}
