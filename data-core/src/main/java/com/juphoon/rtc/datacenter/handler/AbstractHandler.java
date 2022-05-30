package com.juphoon.rtc.datacenter.handler;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.processor.IProcessor;
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
public abstract class AbstractHandler<T> implements IHandler<T> {
    protected IProcessor<T> processor;

    /**
     *
     * @param eventType
     * @return
     */
    @Override
    public boolean care(EventType eventType) {
        List<EventType> eventTypes = careEvents();

        if (eventTypes != null) {
            return eventTypes.contains(eventType);
        } else {
            return false;
        }
    }
}
