package com.juphoon.rtc.datacenter.mq;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.IEventHandler;
import com.juphoon.rtc.datacenter.handler.inner.FirstInnerEventHandler;
import com.juphoon.rtc.datacenter.handler.inner.LastInnerEventHandler;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
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

    /**
     * if(当前handler是否关心当前事件){
     *      return
     * }
     * if(当前事件是重做的且重做列表中不包含当前handler){
     *     return
     * }
     * if(失败) {
     *   if(当前handle是否需要重做){
     *     重做
     *   }
     * } else (成功) {
     *   if(是否曾经重做过){
     *     删除重做记录
     *   }
     * }
     *
     * @param eventContext
     * @throws Exception
     */
    @Override
    public void onEvent(EventContext eventContext) throws Exception {
        List<IEventHandler> eventHandlers = processor.getEventHandlers();
        assert null != eventHandlers : "handlers 为空";
        for (IEventHandler eventHandler : eventHandlers) {
            try {
                String handleName = eventHandler.getClass().getName();
                if (handleName.contains("$")) {
                    handleName = handleName.substring(0, handleName.indexOf("$"));
                }
                if (!eventHandler.care(eventContext.getEvent())) {
                    return;
                }
                if (eventContext.getRetryCount() > 0 && !eventContext.getRedoClzMap().containsKey(handleName)
                        && !(eventHandler instanceof FirstInnerEventHandler) && !(eventHandler instanceof LastInnerEventHandler)){
                    return;
                }
                log.info("执行handle:{}中, ec :{}", handleName, eventContext);
                //TODO 记录handle处理成功失败  处理时间  actor
                if (!eventHandler.handle(eventContext)) {
                    // TODO 处理失败, redo等
                    log.info("处理失败handle:{}中, ec :{}", handleName, eventContext);
                    //判断当前handler是否支持重做 且 当前事件没有重做过
                    if (eventHandler.isRedo() && eventContext.getRetryCount() == 0) {
                        log.info("进入重做队列handle:{}中, ec :{}", handleName, eventContext);
                        queueService.redo(eventContext,handleName);
                    }
                } else {
                    log.info("处理成功handle:{}中, ec :{}", handleName, eventContext);
                    if (eventContext.getRetryCount() > 0
                            && !(eventHandler instanceof FirstInnerEventHandler) && !(eventHandler instanceof LastInnerEventHandler)) {
                        String redoId = eventContext.getRedoClzMap().remove(handleName);
                        queueService.redoOk(redoId);
                    }
                }
            } catch (Exception e) {
                log.error("{}", e);
                log.error("EventContext:{} , eventHandler: {}执行失败", eventContext, eventHandler);
            }
        }
    }
}
