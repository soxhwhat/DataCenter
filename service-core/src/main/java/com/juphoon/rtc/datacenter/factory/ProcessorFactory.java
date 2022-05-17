package com.juphoon.rtc.datacenter.factory;

import com.juphoon.rtc.datacenter.exception.JrtcInvalidProcessorConfigurationException;
import com.juphoon.rtc.datacenter.processor.IEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * <p>Processor工厂</p>
 *
 * processor 工厂依赖 spring bean 工厂，因此所有的 processor都需要被 spring 工厂托管
 * 也就是都需要加 @Service 或其他类似注解
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/16/22 8:54 AM
 */
@Component
@Slf4j
public class ProcessorFactory {
    @Autowired
    private Map<String, IEventProcessor> processors;

    @PostConstruct
    public void init() {
        assert null != processors : "** processor不能为空 **";
    }

    /**
     * 通过传入的processor名称获取对应的process实例
     * @param name
     * @return
     */
    public IEventProcessor getProcessor(String name) throws JrtcInvalidProcessorConfigurationException {
        IEventProcessor processor = processors.get(name);

        if (null == processor) {
            log.warn("** 无效的 processor 名:" + name + " **");
            throw new JrtcInvalidProcessorConfigurationException("** 无效的 processor 名:" + name + " **");
        }

        return processor;
    }
}
