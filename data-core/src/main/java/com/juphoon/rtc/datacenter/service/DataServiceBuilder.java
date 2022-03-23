package com.juphoon.rtc.datacenter.service;

import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import com.juphoon.rtc.datacenter.processor.EventProcessorBuilder;
import com.juphoon.rtc.datacenter.processor.IEventProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 20:00
 * @Description:
 */
@Slf4j
public class DataServiceBuilder {

    public static Builder processors() {
        return new Builder();
    }

    public static class Builder {
        private List<IEventProcessor> processors = new ArrayList<>();

        public EventProcessorBuilder.Builder processor(AbstractEventProcessor processor) {
            assert null != processor : "processor不能为空";

            return EventProcessorBuilder.newProcessor(processor, this);
        }

        public void addProcessor(AbstractEventProcessor processor) {
            if (processor.isEnabled()) {
                log.info("* processor {} disabled *", processor.getName());
                processors.add(processor);
            }
        }

        // todo dataservice 需要把 processor 模块包装起来
        public DataService build() {
            return new DataService(processors);
        }
    }
}