package com.juphoon.rtc.datacenter.servicecore.processor;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.ProcessorId;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractEventProcessor;
import com.juphoon.rtc.datacenter.servicecore.property.DataCenterProperties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_RELIABLE;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 9:33
 * @Description:
 */
@Getter
@Component
public class KafkaProcessor extends AbstractEventProcessor {
    @Autowired
    @Qualifier(EVENT_BIN_LOG_IMPL_RELIABLE)
    private ILogService<EventContext> eventLogService;

    @Autowired
    private DataCenterProperties properties;

    @Override
    public ILogService<EventContext> logService() {
        return eventLogService;
    }

    @Override
    public ProcessorId processorId() {
        return ProcessorId.KAFKA;
    }
}
