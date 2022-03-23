package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.handler.inner.FirstInnerEventHandler;
import com.juphoon.rtc.datacenter.service.DataServiceBuilder;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import com.juphoon.rtc.datacenter.handler.inner.LastInnerEventHandler;
import com.juphoon.rtc.datacenter.mq.EventQueueConfig;
import com.juphoon.rtc.datacenter.mq.EventQueueService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>processor构造器</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 11:12 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class EventProcessorBuilder {
    public static Builder newProcessor(AbstractEventProcessor processor,
                                       DataServiceBuilder.Builder dataServiceBuilder) {
        return new Builder(processor, dataServiceBuilder);
    }

    public static class Builder {
        private AbstractEventProcessor processor;

        private DataServiceBuilder.Builder dataServiceBuilder;

        public Builder(AbstractEventProcessor processor, DataServiceBuilder.Builder dataServiceBuilder) {
            this.processor = processor;
            this.dataServiceBuilder = dataServiceBuilder;
        }

        private EventQueueService eventQueueService;

        private List<AbstractEventHandler> handlers = new ArrayList<>();

        /**
         * 返回data service构造器
         *
         * @return
         */
        public DataServiceBuilder.Builder end() {
            if (null == this.eventQueueService) {
                this.eventQueueService = new EventQueueService(this.processor);
            }

            this.processor.setQueueService(this.eventQueueService);
            this.processor.addEventHandler(new FirstInnerEventHandler());
            this.processor.addEventHandlers(handlers);
            this.processor.addEventHandler(new LastInnerEventHandler(eventQueueService));

            dataServiceBuilder.addProcessor(processor);
            return dataServiceBuilder;
        }

        /**
         * 构造消息队列
         * TODO 这里是否需要外部传参
         *
         * @param config
         * @return
         */
        public Builder mq(EventQueueConfig config) {
            this.eventQueueService = new EventQueueService(config, processor);
            return this;
        }

        /**
         * 添加处理句柄
         * TODO 这里是否需要外部传参
         *
         * @param handler
         * @return
         */
        public Builder handler(AbstractEventHandler handler) {
            handler.setProcessor(this.processor);
            handlers.add(handler);
            return this;
        }
    }
}
