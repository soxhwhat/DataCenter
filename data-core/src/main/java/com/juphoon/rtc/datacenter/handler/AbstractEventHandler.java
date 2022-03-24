package com.juphoon.rtc.datacenter.handler;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.ICare;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import com.juphoon.rtc.datacenter.service.LogService;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
@Setter
public abstract class AbstractEventHandler implements IEventHandler, ICare {

    public AbstractEventProcessor processor;
    public LogService logService;

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
        return true;
    }

    /**
     * 是否启用
     */
    private boolean enabled = true;

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

    /**
     * handler名称
     */
    private String name = this.toString();

    /**
     * 获取名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

}
