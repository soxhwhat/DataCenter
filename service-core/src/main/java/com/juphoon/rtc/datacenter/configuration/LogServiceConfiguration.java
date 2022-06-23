package com.juphoon.rtc.datacenter.configuration;

import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.exception.JrtcInvalidProcessorConfigurationException;
import com.juphoon.rtc.datacenter.factory.HandlerFactory;
import com.juphoon.rtc.datacenter.factory.ProcessorFactory;
import com.juphoon.rtc.datacenter.processor.IProcessor;
import com.juphoon.rtc.datacenter.processor.queue.IQueueService;
import com.juphoon.rtc.datacenter.property.DataCenterProperties;
import com.juphoon.rtc.datacenter.service.EventService;
import com.juphoon.rtc.datacenter.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>产品默认构造器</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 */
@Slf4j
@Configuration
public class LogServiceConfiguration {

    @Autowired
    private DataCenterProperties properties;

    @Autowired
    private ProcessorFactory processorFactory;

    @Autowired
    private HandlerFactory handlerFactory;

    public DataCenterProperties getProperties() {
        return properties;
    }

    @Bean
    public LogService logService() throws JrtcInvalidProcessorConfigurationException {
        if (CollectionUtils.isEmpty(getProperties().getEventProcessors())) {
            log.warn("** log processors 功能关闭 **");
            log.warn("** log processors 功能关闭 **");
            return new LogService(new LinkedList<>());
        }

//        assert !CollectionUtils.isEmpty(getProperties().getLogProcessors()) : "** processors 不能为空，请检查配置! **";

        List<IProcessor<LogContext>> processors = new ArrayList<>(getProperties().getLogProcessors().size());

        for (DataCenterProperties.Processor config : getProperties().getLogProcessors()) {
            assert !StringUtils.isEmpty(config.getName()) : "** processor name 不能为空，请检查配置! **";
            assert !CollectionUtils.isEmpty(config.getHandlers()) : "** handlers 不能为空，请检查配置! **";

            IProcessor<LogContext> processor = processorFactory.getLogProcessor(config.getName());
            /// 构造queueService
            processor.buildQueueService(config.getQueueService());
            processor.start();

            config.getHandlers().forEach(handlerName -> processor.addHandler(handlerFactory.getLogHandler(handlerName.getId())));

            processors.add(processor);
        }

        return new LogService(processors);
    }
}
