package com.juphoon.rtc.datacenter.handler.inner;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.handler.AbstractStateHandler;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
public class TestStateHandler extends AbstractStateHandler {
    @Override
    public boolean care(EventType eventType) {
        return false;
    }

    @Override
    public List<EventType> careEvents() {
        return null;
    }

    @Override
    public boolean handle(StateContext context) throws Exception {
        return false;
    }

    @Override
    public HandlerId handlerId() {
        return null;
    }
}
