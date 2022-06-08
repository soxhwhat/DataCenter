package com.juphoon.rtc.datacenter.api;

import com.juphoon.rtc.datacenter.exception.JrtcUnknownEventException;
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
public class LogContext extends BaseContext {
    public LogContext(@NotNull List<String> logs) {
        this.logs = logs;
    }

    private EventType eventType;

    private int type;

    private int number;

    /**
     * 内容
     */
    @NotNull
    private final List<String> logs;

    @Override
    public EventType getEventType() {
        if (null == eventType) {
            if (EventType.CLIENT_LOG_EVENT.getType().equals(type) && EventType.CLIENT_LOG_EVENT.getNumber().equals(number)) {
                eventType = EventType.CLIENT_LOG_EVENT;
            } else if (EventType.SERVER_LOG_EVENT.getType().equals(type) && EventType.SERVER_LOG_EVENT.getNumber().equals(number)) {
                eventType = EventType.SERVER_LOG_EVENT;
            } else {
                throw new JrtcUnknownEventException(type + ":" + number);
            }
        }

        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
        this.type = eventType.getType();
        this.number = eventType.getNumber();
    }
}
