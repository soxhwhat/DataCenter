package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.handler.IEventHandler;
import cloud.juphoon.jrtc.processor.AbstractEventProcessor;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/24 15:30
 * @Description:
 */
@Slf4j
public class DisruptorEventHandle implements WorkHandler<EventContext> {

    private AbstractEventProcessor processor;
    private AbstractEventQueueService queueService;

    public DisruptorEventHandle(AbstractEventProcessor processor, AbstractEventQueueService queueService) {
        this.processor = processor;
        this.queueService = queueService;
    }

    @Override
    public void onEvent(EventContext eventContext) throws Exception {
        List<IEventHandler> eventHandlers = processor.getEventHandlers();
        for (IEventHandler eventHandler : eventHandlers) {
            try {
                if (eventHandler.care(eventContext.getEvent())) {
                    log.info("执行handle:{}中, ec :{}", eventHandler.getClass().getName(), eventContext);
                    //TODO 记录handle处理成功失败  处理时间  actor
                    if (!eventHandler.handle(eventContext)) {
                        // TODO 处理失败, redo等
                        log.info("处理失败handle:{}中, ec :{}", eventHandler.getClass().getName(), eventContext);
                        if (eventHandler.isRedo()) {
                            log.info("进入重做队列handle:{}中, ec :{}", eventHandler.getClass().getName(), eventContext);
                            queueService.redo(eventContext);
                        }
                    } else {
                        log.info("处理成功handle:{}中, ec :{}", eventHandler.getClass().getName(), eventContext);
                        if (eventContext.getRetryCount() > 0) {
                            queueService.redoOk(eventContext);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("{}", e);
                log.error("EventContext:{} , eventHandler: {}执行失败", eventContext, eventHandler);
            }
        }
    }
}
