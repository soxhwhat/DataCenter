package com.juphoon.rtc.datacenter.handler;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.ICare;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
@Setter
public abstract class AbstractEventHandler implements IEventHandler, ICare {

    protected AbstractEventProcessor processor;

    @Override
    public boolean care(Event event) {
        List<EventType> eventTypes = careEvents();
        if (eventTypes != null) {
            return eventTypes.contains(event.getEventType());
        } else {
            return false;
        }
    }

    @Override
    public boolean isRedo() {
        return false;
    }
}
