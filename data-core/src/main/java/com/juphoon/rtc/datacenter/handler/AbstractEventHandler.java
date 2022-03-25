package com.juphoon.rtc.datacenter.handler;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.ICare;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
@Setter
@Getter
public abstract class AbstractEventHandler implements IEventHandler, ICare {
    /**
     * 是否启用(外界配置)
     */
    private boolean enabled = true;

    public AbstractEventProcessor processor;

    /**
     * 是否启用
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 设置启用
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean care(Event event) {
        List<EventType> eventTypes = careEvents();
        if (eventTypes != null) {
            return eventTypes.contains(event.getEventType());
        } else {
            return false;
        }
    }


}
