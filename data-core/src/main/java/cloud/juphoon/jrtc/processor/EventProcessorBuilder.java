package cloud.juphoon.jrtc.processor;

import cloud.juphoon.jrtc.handler.IEventHandler;
import cloud.juphoon.jrtc.handler.inner.FirstInnerEventHandler;
import cloud.juphoon.jrtc.handler.inner.LastInnerEventHandler;
import cloud.juphoon.jrtc.mq.EventQueueConfig;
import cloud.juphoon.jrtc.mq.EventQueueService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 11:12 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class EventProcessorBuilder {


    public static Builder newProcessor(AbstractEventProcessor processor) {
        return new Builder(processor);
    }

    public static class Builder {
        private AbstractEventProcessor processor;

        public Builder(AbstractEventProcessor processor) {
            this.processor = processor;

        }

        private EventQueueService eventQueueService;

        private List<IEventHandler> handlers = new ArrayList<>();

        /**
         * 构造消息队列
         *
         * @param config
         * @return
         */
        public Builder mq(EventQueueConfig config) {
            this.eventQueueService = new EventQueueService(config,processor);
            return this;
        }

        /**
         * 添加处理句柄
         *
         * @param handler
         * @return
         */
        public Builder handler(IEventHandler handler) {
            handlers.add(handler);
            return this;
        }

        public IEventProcessor build() {
            // TODO
            if (null == this.eventQueueService) {
                this.eventQueueService = new EventQueueService(this.processor);
            }

            this.processor.setQueueService(this.eventQueueService);
            this.processor.addEventHandler(new FirstInnerEventHandler());
            this.processor.addEventHandlers(handlers);
            this.processor.addEventHandler(new LastInnerEventHandler(eventQueueService));

            return processor;
        }
    }
}
