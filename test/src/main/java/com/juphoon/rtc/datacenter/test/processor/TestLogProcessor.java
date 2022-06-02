package com.juphoon.rtc.datacenter.test.processor;

import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.handler.inner.FirstInnerHandler;
import com.juphoon.rtc.datacenter.processor.AbstractLogProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.LOG_BIN_LOG_IMPL_NONE;

/**
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 10:39 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
public class TestLogProcessor extends AbstractLogProcessor {
    @Autowired
    @Qualifier(LOG_BIN_LOG_IMPL_NONE)
    private ILogService<LogContext> logService;

    @Override
    public ILogService<LogContext> logService() {
        return logService;
    }

    @Override
    public ProcessorId processorId() {
        return ProcessorId.TEST_LOG;
    }

    @Override
    public FirstInnerHandler<LogContext> firstInnerEventHandler() {
        return new FirstInnerHandler<>(this);
    }
}

