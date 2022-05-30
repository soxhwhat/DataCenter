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
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
public class Yhandler extends AbstractEventHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.Yhandler;
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
