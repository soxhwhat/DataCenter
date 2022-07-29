package com.juphoon.rtc.datacenter.servicecore.processor;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.ProcessorId;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractEventProcessor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;
import static com.juphoon.rtc.datacenter.datacore.api.ProcessorId.UPLOAD_DB;

/**
 * <p>upload数据源processor</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author  ke.wang@juphoon.com
 * @date    2022/7/28 10:25
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
@Getter
public class UploadDbProcessor extends AbstractEventProcessor {

    @Autowired
    @Qualifier(EVENT_BIN_LOG_IMPL_FLASH)
    private ILogService<EventContext> eventLogService;

    @Override
    public ProcessorId processorId() {
        return UPLOAD_DB;
    }

    @Override
    public ILogService<EventContext> logService() {
        return eventLogService;
    }
}
