package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/3 16:03
 * @Description:
 */
@Getter
@Component
public class MongoEventProcessor extends AbstractEventProcessor {
    @Autowired
    @Qualifier(EVENT_BIN_LOG_IMPL_FLASH)
    private ILogService<EventContext> eventLogService;

    @Override
    public ILogService<EventContext> logService() {
        return eventLogService;
    }

    @Override
    public ProcessorId processorId() {
        return ProcessorId.MONGO_EVENT_PROCESSOR;
    }
}
