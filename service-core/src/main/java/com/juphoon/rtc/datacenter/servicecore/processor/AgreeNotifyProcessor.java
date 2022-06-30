package com.juphoon.rtc.datacenter.servicecore.processor;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.ProcessorId;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.handler.AbstractHttpEventHandler;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractHttpEventProcessor;
import com.juphoon.rtc.datacenter.datacore.processor.loader.AbstractContextLoader;
import com.juphoon.rtc.datacenter.datacore.processor.loader.ContextLoaderConfig;
import com.juphoon.rtc.datacenter.servicecore.property.DataCenterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_RELIABLE;

/**
 * <p>通用HTTP连接处理器</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 10:39 AM
 */
@Slf4j
@Component
public class AgreeNotifyProcessor extends AbstractHttpEventProcessor {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier(EVENT_BIN_LOG_IMPL_RELIABLE)
    private ILogService<EventContext> eventLogService;

    @Autowired
    private DataCenterProperties properties;

    private int counter = 0;

    @Override
    public ILogService<EventContext> logService() {
        return eventLogService;
    }

    @Override
    public ProcessorId processorId() {
        return ProcessorId.AGREE;
    }

    @Override
    public boolean exchange(EventContext ec, AbstractHttpEventHandler handler) {
        return handler.exchange(getUrl(), restTemplate, ec);
    }

    /**
     * 轮询获取host
     *
     * @return
     */
    private String getUrl() {
        return properties.getAgree().getHosts().get(counter++ % properties.getAgree().getHosts().size());
    }
}
