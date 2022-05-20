package com.juphoon.rtc.datacenter.processor;

import com.juphoon.iron.component.utils.response.IronException;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.ICare;
import com.juphoon.rtc.datacenter.event.queue.IEventQueueService;
import com.juphoon.rtc.datacenter.handler.AbstractCareAllEventHandler;
import com.juphoon.rtc.datacenter.handler.IEventHandler;
import com.juphoon.rtc.datacenter.handler.inner.FirstInnerEventHandler;
import com.juphoon.rtc.datacenter.handler.inner.LastInnerEventHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 10:39
 * @update:
 * <p>1. 2022-03-22. ajian.zheng 增加处理器名</p>
 * <p>1. 2022-05-16. ajian.zheng 调整结构，eventLog/redoLog/eventQueueService改为设入，非自动装配</p>
 */
@Slf4j
@Getter
public abstract class AbstractEventProcessor implements IEventProcessor, ICare {
    /**
     * handler列表
     */
    private List<IEventHandler> eventHandlers = new ArrayList<>();

    /**
     * 是否关心全局标记
     */
    private boolean isAllCare = false;

    /**
     * 关心事件集合
     */
    private Set<EventType> careEvents = new HashSet<>();

    /**
     * 事件队列处理器
     */
    private IEventQueueService eventQueueService;

    /**
     * springboot 单线程启动
     *
     * @return
     */
    @Override
    public IEventQueueService eventQueueService() {
        if (null != eventQueueService) {
            return eventQueueService;
        }

        eventQueueService = buildMyEventQueueService();

        return eventQueueService;
    }

    /**
     * 内置首个事件处理器
     */
    private FirstInnerEventHandler firstInnerEventHandler = new FirstInnerEventHandler(this);

    /**
     * 内置最后一个事件处理器
     */
    private LastInnerEventHandler lastInnerEventHandler = new LastInnerEventHandler(this);

    @Override
    public String getName() {
        return processorId().getName();
    }

    @Override
    public String getId() {
        return processorId().getId();
    }

    /**
     * processor不需要关心本方法
     *
     * @return 不关心
     */
    @Override
    public List<EventType> careEvents() {
        return null;
    }

    /**
     * 添加处理句柄
     *
     * @param handler 处理器
     */
    @Override
    public void addEventHandler(IEventHandler handler) {
        assert null != handler : "handler 不能为空";

        this.eventHandlers.add(handler);

        if (!isAllCare) {
            //非inner的handle才会影响isAllCare
            if (!(handler instanceof FirstInnerEventHandler || handler instanceof LastInnerEventHandler)) {
                isAllCare = handler instanceof AbstractCareAllEventHandler;
            }
        }

        if (handler.careEvents() != null) {
            careEvents.addAll(handler.careEvents());
        }

    }

//    /**
//     * 添加处理句柄
//     *
//     * @param handlers 处理器集合
//     */
//    public void addEventHandlers(List<AbstractEventHandler> handlers) {
//        assert null != handlers : "handlers 不能为空";
//
//        for (IEventHandler handler : handlers) {
//            addEventHandler(handler);
//        }
//    }

    @Override
    public boolean care(Event event) {
        assert careEvents != null : "关注事件列表必须为非空";
        log.debug("careEvents:{},eventType:{},flag:{}"
                , careEvents, event.getEventType(), careEvents.contains(event.getEventType()));
        return isAllCare || careEvents.contains(event.getEventType());
    }

    /**
     * 目前设计中 route会直接把消息在所有的process中遍历一遍
     * 所以需要判断泛型对象中的msg对象是否是当前process关注的类型
     * 1、需要判断泛型对象中的msg对象是否是当前process关注的类型
     * 2、如果判断成功需要 推送到消息队列中 遍历执行handle
     * 3、如果判断失败则直接返回
     *
     * @param ec 事件
     */
    @Override
    public void process(EventContext ec) {
        log.debug("ec:{}", ec);

        if (!care(ec.getEvent())) {
            log.debug("{} not care {}.{}", getId(), ec.getEvent().getType(), ec.getEvent().getNumber());
            return;
        }

        try {
            eventQueueService().submit(ec);
        } catch (Exception ex) {
            // 入队失败
            log.warn("processor:{} submit ec:{} 异常:e", getName(), ec.getRequestId(), ex);
        }

        /// 先入库
        try {
            if (!ec.isRedoEvent()) {
                eventLogService().saveEventLog(ec, this);
            }
        } catch (Exception ex) {
            log.warn("processor:{} 持久化 ec:{} 异常:", getName(), ec.getRequestId(), ex);
            //感觉没啥用，暂时先这样
            throw new IronException();
        }

        /// 再提交队列
        try {
            eventQueueService().submit(ec);
        } catch (Exception ex) {
            // 入队失败
            log.warn("processor:{} submit ec:{} 异常:e", getName(), ec.getRequestId(), ex);
        }
    }

    /**
     * 重做回调入口(实际工作入口)
     *
     * @param ec 事件
     */
    public void onProcess(EventContext ec) {
        log.debug("ec:{}", ec);

        /// first
        firstInnerEventHandler.handle(ec);

        for (IEventHandler handler : getEventHandlers()) {

            // 不关心
            if (!handler.care(ec.getEvent())) {
                continue;
            }

            if (ec.isRedoEvent()) {
                onRedoEvent(ec, handler);
            } else {
                onFreshEvent(ec, handler);
            }
        }

        /// last todo 异常处理（可能死循环）
        lastInnerEventHandler.handle(ec);
    }

    /**
     * 处理新鲜事件
     *
     * @return void
     */
    private void onFreshEvent(EventContext ec, IEventHandler handler) {
        long start = System.currentTimeMillis();

        try {
            /// 处理成功
            if (handler.handle(ec)) {
                log.debug("{} handle ec:{} 成功, cost:{}",
                        handler.getName(), ec.getId(), System.currentTimeMillis() - start);
            }
            /// 处理失败
            else {
                log.info("{} handle ec:{} 失败", handler.getName(), ec.getId());
                eventLogService().saveRedoLog(ec, handler);
            }
        } catch (Exception e) {
            log.warn("handler {} handle ec:{} 异常:", handler.getName(), ec.getId(), e);
        }
    }

    /**
     * 重做消息统一处理方法
     *
     * @param ec 事件
     * @param handler 处理器
     * @return void
     */
    private void onRedoEvent(EventContext ec, IEventHandler handler) {
        // 重做不包含当前handler
        if (!ec.getRedoHandlerIds().contains(handler.getId())) {
            return;
        }

        try {
            /// 重做成功
            if (handler.handle(ec)) {
                /// 删除ec中的失败标记
                ec.cleanRedoFlag(handler);

                log.info("{} reHandle ec:{} 成功", handler.getName(), ec);

                eventLogService().removeRedoLog(ec);
            }
            /// 重做失败
            else {
                /// 打印日志，啥也不做
                log.info("{} reHandle ec:{} 失败", handler.getName(), ec.getId());
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("handler {} reHandle ec:{} 异常:", handler.getName(), ec.getId(), e);
            }
        }
    }


}
