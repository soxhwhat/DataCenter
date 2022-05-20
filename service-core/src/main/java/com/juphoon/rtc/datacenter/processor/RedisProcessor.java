package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.event.queue.IEventQueueService;
import com.juphoon.rtc.datacenter.event.queue.impl.NoneEventQueueServiceImpl;
import com.juphoon.rtc.datacenter.event.storage.IEventLogService;
import com.juphoon.rtc.datacenter.property.DataCenterProperties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;
import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_NONE;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/3 16:03
 * @Description:
 */
@Getter
@Component
public class RedisProcessor extends AbstractEventProcessor {
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
        return ProcessorId.REDIS;
    }
}
