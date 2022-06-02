package com.juphoon.rtc.datacenter.test.handler;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.handler.AbstractStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
public class TestStateHandler extends AbstractStateHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.TestStateHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.values());
    }

    @Override
    public boolean handle(StateContext context) {
        log.info("{} handle {}:{}", getId(), context.getRequestId(), context.getParams());
        return true;
    }
}
