package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.event.queue.IEventQueueService;
import com.juphoon.rtc.datacenter.event.queue.impl.NoneEventQueueServiceImpl;
import com.juphoon.rtc.datacenter.event.storage.IEventLogService;
import com.juphoon.rtc.datacenter.property.DataCenterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_NONE;

/**
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 10:39 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
public class TestProcessor extends AbstractEventProcessor {
    @Autowired
    @Qualifier(EVENT_BIN_LOG_IMPL_NONE)
    private IEventLogService eventLogService;

    @Autowired
    private DataCenterProperties properties;

    /**
     * todo 修改为正确的 eventQueue
     * @return
     */
    @Override
    public IEventQueueService buildMyEventQueueService() {
        // properties.getXxxConfig()
        return new NoneEventQueueServiceImpl(this);
    }

    @Override
    public IEventLogService eventLogService() {
        return eventLogService;
    }

    @Override
    public ProcessorId processorId() {
        return ProcessorId.TEST;
    }
}

