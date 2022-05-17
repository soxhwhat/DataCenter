package com.juphoon.rtc.datacenter.configuration;

import com.juphoon.rtc.datacenter.exception.JrtcInvalidProcessorConfigurationException;
import com.juphoon.rtc.datacenter.factory.EventQueueFactory;
import com.juphoon.rtc.datacenter.factory.EventStorageFactory;
import com.juphoon.rtc.datacenter.factory.HandlerFactory;
import com.juphoon.rtc.datacenter.factory.ProcessorFactory;
import com.juphoon.rtc.datacenter.processor.IEventProcessor;
import com.juphoon.rtc.datacenter.property.DataCenterProperties;
import com.juphoon.rtc.datacenter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>产品默认构造器</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 */
@Configuration
public class DataServiceConfiguration {

    @Autowired
    private DataCenterProperties properties;

    @Autowired
    private ProcessorFactory processorFactory;

    @Autowired
    private HandlerFactory handlerFactory;

    @Autowired
    private EventQueueFactory queueFactory;

    @Autowired
    private EventStorageFactory storageFactory;

    public DataCenterProperties getProperties() {
        return properties;
    }

    @Bean
    public DataService config() throws JrtcInvalidProcessorConfigurationException {

        assert !CollectionUtils.isEmpty(getProperties().getProcessors()) : "** processors 不能为空，请检查配置! **";

        List<IEventProcessor> processors = new ArrayList<>(getProperties().getProcessors().size());

        for (DataCenterProperties.Processor config : getProperties().getProcessors()) {
            assert !StringUtils.isEmpty(config.getName()) : "** processor name 不能为空，请检查配置! **";
            assert !StringUtils.isEmpty(config.getEventLog()) : "** eventLog 不能为空，请检查配置! **";
            assert !StringUtils.isEmpty(config.getRedoLog()) : "** redoLog 不能为空，请检查配置! **";
            assert !StringUtils.isEmpty(config.getQueueService()) : "** queueService 不能为空，请检查配置! **";
            assert !CollectionUtils.isEmpty(config.getHandlers()) : "** handlers 不能为空，请检查配置! **";

            IEventProcessor processor = processorFactory.getProcessor(config.getName());
            processor.setEventLogService(storageFactory.getEventLogService(config.getEventLog()));
            processor.setRedoLogService(storageFactory.getRedoLogService(config.getRedoLog()));
            processor.setEventQueueService(queueFactory.getEventQueueService(config.getQueueService()));

            config.getHandlers().forEach(handlerName -> processor.addEventHandler(handlerFactory.getHandler(handlerName)));

            processors.add(processor);
        }

        return new DataService(processors);
    }
}
