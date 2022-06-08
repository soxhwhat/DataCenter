package com.juphoon.rtc.datacenter.test.processor;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.handler.inner.FirstInnerHandler;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;

/**
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 10:39 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
public class TestEventProcessor extends AbstractEventProcessor {
    @Autowired
    @Qualifier(EVENT_BIN_LOG_IMPL_FLASH)
    private ILogService<EventContext> eventLogService;

    @Override
    public ILogService<EventContext> logService() {
        return eventLogService;
    }

    @Override
    public ProcessorId processorId() {
        return ProcessorId.TEST_EVENT;
    }

    @Override
    public FirstInnerHandler<EventContext> firstInnerEventHandler() {
        return new FirstInnerHandler<>(this);
    }
}

