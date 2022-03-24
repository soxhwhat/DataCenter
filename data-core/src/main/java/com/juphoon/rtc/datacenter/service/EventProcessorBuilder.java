package com.juphoon.rtc.datacenter.service;

import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import com.juphoon.rtc.datacenter.handler.inner.LastInnerEventHandler;
import com.juphoon.rtc.datacenter.mq.EventQueueConfig;
import com.juphoon.rtc.datacenter.mq.service.AbstractEventQueueService;
import com.juphoon.rtc.datacenter.mq.service.impl.DisruptorEventQueueServiceImpl;
import com.juphoon.rtc.datacenter.mq.service.impl.SampleEventQueueServiceImpl;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
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

        private DataServiceBuilder.Builder dataServiceBuilder;

        public Builder(AbstractEventProcessor processor, DataServiceBuilder.Builder dataServiceBuilder) {
            this.processor = processor;
            this.dataServiceBuilder = dataServiceBuilder;
        }

        private AbstractEventQueueService eventQueueService;

        private List<AbstractEventHandler> handlers = new ArrayList<>();

        /**
         * 返回data service构造器
         *
         * @return
         */
        public DataServiceBuilder.Builder end() {
            if (null == this.eventQueueService) {
                mq(new EventQueueConfig());
            }

            this.processor.setQueueService(this.eventQueueService);
            /// 自动装载
//            this.processor.addEventHandler(new FirstInnerEventHandler());

            this.processor.addEventHandlers(handlers);

            /// 自动装载
//            this.processor.addEventHandler(new FirstInnerEventHandler());
            this.processor.addEventHandler(new LastInnerEventHandler());

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
            if (JrtcDataCenterConstant.DATA_CENTER_QUEUE_MODE_SIMPLE.equalsIgnoreCase(config.getType())) {
                this.eventQueueService = new SampleEventQueueServiceImpl();
            } else {
                this.eventQueueService = new DisruptorEventQueueServiceImpl(config);
            }
            this.eventQueueService.setProcessor(processor);

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
            } else {
                handler.setProcessor(this.processor);
                handlers.add(handler);
            }

            return this;
        }
    }
}
