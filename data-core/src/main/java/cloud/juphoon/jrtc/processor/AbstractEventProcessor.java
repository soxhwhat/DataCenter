package cloud.juphoon.jrtc.processor;

import cloud.juphoon.jrtc.api.Event;
import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.EventType;
import cloud.juphoon.jrtc.api.ICare;
import cloud.juphoon.jrtc.handler.IEventHandler;
import cloud.juphoon.jrtc.handler.inner.FirstInnerEventHandler;
import cloud.juphoon.jrtc.handler.inner.LastInnerEventHandler;
import cloud.juphoon.jrtc.mq.EventQueueService;
import cloud.juphoon.jrtc.mq.IEventQueue;
import cloud.juphoon.jrtc.mq.IEventQueueService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 10:39
 * @Description:
 */
@Slf4j
@Setter
@Getter
public abstract class AbstractEventProcessor implements IEventProcessor, ICare {

    /**
     * 注册需要关注的类型
     * 只有关注的类型才会被当前process消费
     * 由service模块注入
     */

    private IEventQueueService queueService;

    private List<IEventHandler> eventHandlers = new ArrayList<>();

    private boolean isAllCare = false;

    /**
     * 设置队列服务
     *
     * @param queueService
     */
    public void setQueueService(IEventQueueService queueService) {
        this.queueService = queueService;
    }

    /**
     * 添加处理句柄
     *
     * @param handler
     */
    public void addEventHandler(IEventHandler handler) {
        assert null != handler : "handler 为空";
        this.eventHandlers.add(handler);
        if (handler.careEvents() != null) {
            this.addAllCare(handler.careEvents());
        }
        //非inner的handle才会影响isAllCare
        if (!(handler instanceof FirstInnerEventHandler || handler instanceof LastInnerEventHandler)) {
            isAllCare |= handler.isAllCare();
        }
    }

    /**
     * 添加处理句柄
     *
     * @param handlers
     */
    public void addEventHandlers(List<IEventHandler> handlers) {
        assert null != handlers : "handlers 为空";
        this.eventHandlers.addAll(handlers);
        for (IEventHandler handler : handlers) {
            if (handler.careEvents() != null) {
                this.addAllCare(handler.careEvents());
            }
            //非inner的handle才会影响isAllCare
            if (!(handler instanceof FirstInnerEventHandler || handler instanceof LastInnerEventHandler)) {
                isAllCare |= handler.isAllCare();
            }
        }
    }

    @Override
    public boolean care(Event event) {
        assert careEvents != null : "关注事件列表必须为非空";
        return isAllCare || careEvents.contains(event.getEventType());
    }

    /**
     * 目前设计中 route会直接把消息在所有的process中遍历一遍
     * 所以需要判断泛型对象中的msg对象是否是当前process关注的类型
     * 1、需要判断泛型对象中的msg对象是否是当前process关注的类型
     * 2、如果判断成功需要 推送到消息队列中 遍历执行handle
     * 3、如果判断失败则直接返回
     *
     * @param ec
     */
    @Override
    public void process(EventContext ec) {
        if (care(ec.getEvent()) || isAllCare) {
            queueService.submit(ec);
        }

    }

}
