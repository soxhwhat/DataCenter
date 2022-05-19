package com.juphoon.rtc.datacenter.handle.test;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.handler.AbstractCareAllEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ajian.zheng@juphoon.com
 * @date 2/16/22 10:19 AM
 */
@Slf4j
@Component
public class Xhandler extends AbstractCareAllEventHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.Xhandler;
    }

    @Override
    public boolean handle(EventContext ec) {
        log.info("{} handle {}", getId(), ec);
        return true;
    }
}
