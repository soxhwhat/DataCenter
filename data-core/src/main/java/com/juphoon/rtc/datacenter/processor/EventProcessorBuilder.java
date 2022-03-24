package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.handler.inner.FirstInnerEventHandler;
import com.juphoon.rtc.datacenter.mq.service.LogService;
import com.juphoon.rtc.datacenter.service.DataServiceBuilder;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import com.juphoon.rtc.datacenter.handler.inner.LastInnerEventHandler;
import com.juphoon.rtc.datacenter.mq.EventQueueConfig;
import com.juphoon.rtc.datacenter.mq.EventQueueService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>processor构造器</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 11:12 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public class EventProcessorBuilder {
    public static Builder newProcessor(AbstractEventProcessor processor,
                                       DataServiceBuilder.Builder dataServiceBuilder) {
        return new Builder(processor, dataServiceBuilder);
    }

    public static class Builder {
        private AbstractEventProcessor processor;
        private LogService logService;

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
                this.eventQueueService = new EventQueueService(processor,logService);
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
            this.eventQueueService = new EventQueueService(config,processor,this.logService);
            return this;
        }

        /**
         * 添加处理句柄
         *
         * @param handler
         * @return
         */
        public Builder handler(AbstractEventHandler handler) {
            assert null != handler : "handler 不能为空";

            if (!handler.isEnabled()) {
                log.info("* handler {} disabled *", handler.getName());
            }

            handler.setProcessor(this.processor);
            handler.setLogService(this.logService);
            handlers.add(handler);
            return this;
        }
        /**
         * 添加处理句柄
         *
         * @param logService
         * @return
         */
        public Builder logService(LogService logService) {
            this.logService = logService;
            return this;
        }

        public IEventProcessor build() {
            // TODO
            if (null == this.eventQueueService) {
                this.eventQueueService = new EventQueueService(this.processor,this.logService);
            }

            this.processor.setQueueService(this.eventQueueService);
            this.processor.addEventHandler(new FirstInnerEventHandler());
            this.processor.addEventHandlers(handlers);
            this.processor.addEventHandler(new LastInnerEventHandler(eventQueueService));

            return processor;
        }
    }
}
