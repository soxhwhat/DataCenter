package cloud.juphoon.jrtc.service;

import cloud.juphoon.jrtc.processor.EventProcessorBuilder;
import cloud.juphoon.jrtc.processor.IEventProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 20:00
 * @Description:
 */
public class DataServiceBuilder {

    public static Builder processors() {
        return new Builder();
    }

    public static class Builder {
        private List<IEventProcessor> processors = new ArrayList<>();

        public Builder processor(IEventProcessor processor) {
            assert null != processor : "";

            processors.add(processor);

            return this;
        }

        // todo dataservice 需要把 processor 模块包装起来
        public DataService build() {
            return new DataService(processors);
        }
    }
}