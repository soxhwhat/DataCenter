package com.juphoon.rtc.datacenter.servicecore.configuration;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.exception.JrtcInvalidProcessorConfigurationException;
import com.juphoon.rtc.datacenter.datacore.handler.IHandler;
import com.juphoon.rtc.datacenter.datacore.processor.IProcessor;
import com.juphoon.rtc.datacenter.datacore.service.EventService;
import com.juphoon.rtc.datacenter.servicecore.factory.HandlerFactory;
import com.juphoon.rtc.datacenter.servicecore.factory.ProcessorFactory;
import com.juphoon.rtc.datacenter.servicecore.property.DataCenterProperties;
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
public class EventServiceConfiguration {

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
    public EventService eventService() throws JrtcInvalidProcessorConfigurationException {

        if (CollectionUtils.isEmpty(getProperties().getEventProcessors())) {
            log.warn("** event processors 功能关闭 **");
            log.warn("** event processors 功能关闭 **");
            return new EventService(new LinkedList<>());
        }

//        assert !CollectionUtils.isEmpty(getProperties().getEventProcessors()) : "** processors 不能为空，请检查配置! **";

        List<IProcessor<EventContext>> processors = new ArrayList<>(getProperties().getEventProcessors().size());

        for (DataCenterProperties.Processor config : getProperties().getEventProcessors()) {
            assert !StringUtils.isEmpty(config.getName()) : "** processor name 不能为空，请检查配置! **";
//            assert !StringUtils.isEmpty(config.getEventLog()) : "** eventLog 不能为空，请检查配置! **";
//            assert !StringUtils.isEmpty(config.getRedoLog()) : "** redoLog 不能为空，请检查配置! **";
//            assert !StringUtils.isEmpty(config.getQueueService()) : "** queueService 不能为空，请检查配置! **";
            assert !CollectionUtils.isEmpty(config.getHandlers()) : "** handlers 不能为空，请检查配置! **";

            IProcessor<EventContext> processor = processorFactory.getEventProcessor(config.getName());
            /// 构造queueService
            processor.buildQueueService(config.getQueueService());
            processor.buildContextLoader(config.getContextLoader());
            processor.start();

            config.getHandlers().forEach(handlerName -> {
                IHandler<EventContext> eventHandler = handlerFactory.getEventHandler(handlerName.getId());
                processor.addHandler(eventHandler);
                eventHandler.setProcessor(processor);
            });

            processors.add(processor);
        }

        return new EventService(processors);
    }
}
