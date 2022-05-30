package com.juphoon.rtc.datacenter.handle.test;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author ajian.zheng@juphoon.com
 * @date 2/16/22 10:19 AM
 */
@Slf4j
@Component
public class Xhandler extends AbstractEventHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.Xhandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.values());
    }

    @Override
    public boolean handle(EventContext ec) {
        log.info("{} handle {}", getId(), ec);
        return true;
    }
}
