package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.handler.AbstractHttpEventHandler;
import com.juphoon.rtc.datacenter.property.DataCenterProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_RELIABLE;

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

    private AgreeNotifyProcessor.Config config;

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
     * 设置配置文件
     *
     * @param config
     */
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * 轮询获取host
     *
     * @return
     */
    private String getUrl() {
        return config.getHosts().get(counter++ % config.getHosts().size());
    }

    @Getter
    @Setter
    public static class Config {
        /**
         * 目标地址
         * target = http(s)://ip:port/target,http(s)://ip:port/target,
         * target = http(s)://domain/target
         */
        private List<String> hosts;
    }
}
