package com.juphoon.rtc.datacenter.handler.inner;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.handler.AbstractLogHandler;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
public class TestLogHandler extends AbstractLogHandler {
    @Override
    public boolean care(EventType eventType) {
        return false;
    }

    @Override
    public List<EventType> careEvents() {
        return null;
    }

    @Override
    public boolean handle(LogContext logContext) throws Exception {
        return false;
    }

    @Override
    public HandlerId handlerId() {
        return null;
    }
}
