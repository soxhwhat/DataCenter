package com.juphoon.rtc.datacenter.service;

import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
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

        void addProcessor(AbstractEventProcessor processor) {
            if (!processor.isEnabled()) {
                log.info("* processor {} disabled *", processor.getName());
            } else {
                processors.add(processor);
            }
        }

        public DataService build() {
            return new DataService(processors);
        }
    }
}