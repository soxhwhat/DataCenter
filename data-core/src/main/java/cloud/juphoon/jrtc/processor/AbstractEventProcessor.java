package cloud.juphoon.jrtc.processor;

import cloud.juphoon.jrtc.api.Event;
import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.EventType;
import cloud.juphoon.jrtc.handler.IEventHandler;
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
public abstract class AbstractEventProcessor implements IEventProcessor {

    /**
     * 注册需要关注的类型
     * 只有关注的类型才会被当前process消费
     * 由service模块注入
     */
    private Set<EventType> careEvents;

    private IEventQueueService queueService;

    private List<IEventHandler> eventHandlers = new ArrayList<>();

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
    }

    /**
     * 添加处理句柄
     *
     * @param handlers
     */
    public void addEventHandlers(List<IEventHandler> handlers) {
        assert null != handlers : "handlers 为空";
        this.eventHandlers.addAll(handlers);
    }

    @Override
    public boolean care(Event event) {
        assert careEvents != null : "关注事件列表必须为非空";

        return careEvents.contains(event.getEventType());
    }

    /**
     * 目前设计中 route会直接把消息在所有的process中遍历一遍
     * 所以需要判断泛型对象中的msg对象是否是当前process关注的类型
     * 1、需要判断泛型对象中的msg对象是否是当前process关注的类型
     * 2、如果判断成功需要 推送到消息队列中 遍历执行handle
     * 3、如果判断失败则直接返回
     * @param ec
     */
    @Override
    public void process(EventContext ec) throws Exception {
        if (care(ec.getEvent())) {
            queueService.submit(ec);
        }

    }

    /**
     * 线程池消费事件
     *
     * @return
     */
    @Override
    public boolean handle(EventContext ec) {
        eventHandlers.forEach(handler -> {
            if (!handler.handle(ec)) {
                // TODO 处理失败, redo等
                // 重做
                ec.redo(handler.getClass().getName());
            }
        });
        return true;
    }
}
