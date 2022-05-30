package com.juphoon.rtc.datacenter.processor;

import com.juphoon.iron.component.utils.response.IronException;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.IEventHandler;
import com.juphoon.rtc.datacenter.handler.inner.LastInnerHandler;
import com.juphoon.rtc.datacenter.processor.queue.QueueServiceConfig;
import com.juphoon.rtc.datacenter.processor.queue.impl.DisruptorEventQueueServiceImpl;
import com.juphoon.rtc.datacenter.processor.queue.impl.NoneEventQueueServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.QUEUE_SERVICE_CONFIG_TYPE_DISRUPTOR;
import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.QUEUE_SERVICE_CONFIG_TYPE_NONE;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 10:39
 * @update:
 * <p>1. 2022-03-22. ajian.zheng 增加处理器名</p>
 * <p>2. 2022-05-16. ajian.zheng 调整结构，eventLog/redoLog/eventQueueService改为设入，非自动装配</p>
 * <p>3. 2022-05-31. ajian.zheng 魔改</p>
 */
@Slf4j
@Getter
public abstract class AbstractEventProcessor extends AbstractProcessor<EventContext> {
    @Override
    public LastInnerHandler<EventContext> lastInnerEventHandler() {
        return new LastInnerHandler<>(this);
    }

    @Override
    public void buildQueueService(QueueServiceConfig config) {
        switch (config.getType()) {
            case QUEUE_SERVICE_CONFIG_TYPE_DISRUPTOR:
                setQueueService(new DisruptorEventQueueServiceImpl(this, config));
                break;
            case QUEUE_SERVICE_CONFIG_TYPE_NONE:
                setQueueService(new NoneEventQueueServiceImpl(this, config));
                break;
            default:
                throw new IllegalArgumentException("无效的 QueueService 类型:" + config.getType() + "," + getId());
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
