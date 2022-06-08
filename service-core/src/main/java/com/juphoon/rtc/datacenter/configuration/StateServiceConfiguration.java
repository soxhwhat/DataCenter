package com.juphoon.rtc.datacenter.configuration;

import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.exception.JrtcInvalidProcessorConfigurationException;
import com.juphoon.rtc.datacenter.factory.HandlerFactory;
import com.juphoon.rtc.datacenter.factory.ProcessorFactory;
import com.juphoon.rtc.datacenter.processor.IProcessor;
import com.juphoon.rtc.datacenter.property.DataCenterProperties;
import com.juphoon.rtc.datacenter.service.StateService;
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
public class StateServiceConfiguration {

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
    public StateService stateService() throws JrtcInvalidProcessorConfigurationException {

        assert !CollectionUtils.isEmpty(getProperties().getStateProcessors()) : "** processors 不能为空，请检查配置! **";

        List<IProcessor<StateContext>> processors = new ArrayList<>(getProperties().getLogProcessors().size());

        for (DataCenterProperties.Processor config : getProperties().getStateProcessors()) {
            assert !StringUtils.isEmpty(config.getName()) : "** processor name 不能为空，请检查配置! **";
            assert !CollectionUtils.isEmpty(config.getHandlers()) : "** handlers 不能为空，请检查配置! **";

            IProcessor<StateContext> processor = processorFactory.getStateProcessor(config.getName());
            /// 构造queueService
            processor.buildQueueService(config.getQueueService());
            processor.start();

            config.getHandlers().forEach(handlerName -> processor.addHandler(handlerFactory.getStateHandler(handlerName.getId())));

            processors.add(processor);
        }

        return new StateService(processors);
    }
}
