package com.juphoon.rtc.datacenter.processor;

import com.juphoon.iron.component.utils.response.IronException;
import com.juphoon.rtc.datacenter.api.BasicContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.ICare;
import com.juphoon.rtc.datacenter.handler.IHandler;
import com.juphoon.rtc.datacenter.handler.inner.FirstInnerHandler;
import com.juphoon.rtc.datacenter.handler.inner.LastInnerHandler;
import com.juphoon.rtc.datacenter.processor.queue.IQueueService;
import com.juphoon.rtc.datacenter.processor.queue.QueueServiceConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 10:39
 * @update:
 * <p>1. 2022-03-22. ajian.zheng 增加处理器名</p>
 * <p>1. 2022-05-16. ajian.zheng 调整结构，eventLog/redoLog/eventQueueService改为设入，非自动装配</p>
 */
@Slf4j
@Getter
public abstract class AbstractProcessor<T extends BasicContext> implements IProcessor<T>, ICare {
    /**
     * handler列表
     */
    private List<IHandler<T>> handlers = new ArrayList<>();

    /**
     * 关心事件集合
     */
    private List<EventType> careEvents = new LinkedList<>();

    /**
     * 事件队列处理器
     */
    private IQueueService<T> queueService;

    public void setQueueService(IQueueService<T> queueService) {
        this.queueService = queueService;
    }

    @Override
    public IQueueService<T> queueService() {
        assert null != queueService : "队列服务未初始化";
        return queueService;
    }

    private FirstInnerHandler<T> firstInnerEventHandler;

    /**
     * 内置最后一个事件处理器
     */
    private LastInnerHandler<T> lastInnerEventHandler;

    /**
     * 构造首个内部事件处理handler
     * @return
     */
    public FirstInnerHandler<T> firstInnerEventHandler() {
        return null;
    }

    /**
     * 构造最后一个内部事件处理handler
     * @return
     */
    public abstract LastInnerHandler<T> lastInnerEventHandler();

    public FirstInnerHandler<T> getFirstInnerEventHandler() {
        if (null == firstInnerEventHandler()) {
            return null;
        }

        if (null == firstInnerEventHandler) {
            synchronized (this) {
                if (null == firstInnerEventHandler) {
                    firstInnerEventHandler = firstInnerEventHandler();
                }
            }
        }
        return firstInnerEventHandler;
    }

    public LastInnerHandler<T> getLastInnerEventHandler() {
        if (null == lastInnerEventHandler) {
            synchronized (this) {
                if (null == lastInnerEventHandler) {
                    lastInnerEventHandler = lastInnerEventHandler();
                }
            }
        }
        return lastInnerEventHandler;
    }

    /**
     * processor不需要关心本方法
     *
     * @return 不关心
     */
    @Override
    public List<EventType> careEvents() {
        return careEvents;
    }

    /**
     * 添加处理句柄
     *
     * @param handler 处理器
     */
    @Override
    public void addHandler(IHandler<T> handler) {
        assert null != handler : "handler 不能为空";

        this.handlers.add(handler);

        careEvents.addAll(handler.careEvents());
    }

    @Override
    public boolean care(EventType eventType) {
        return careEvents.contains(eventType);
    }

    /**
     * 目前设计中 route会直接把消息在所有的process中遍历一遍
     * 所以需要判断泛型对象中的msg对象是否是当前process关注的类型
     * 1、需要判断泛型对象中的msg对象是否是当前process关注的类型
     * 2、如果判断成功需要 推送到消息队列中 遍历执行handle
     * 3、如果判断失败则直接返回
     *
     * @param t 事件
     */
    @Override
    public void commit(T t) {
        log.debug("t:{}", t);

        if (!care(t.getEventType())) {
            return;
        }

        t.setProcessorId(this.processorId().getId());

        /// 先入库
        /// TODO 若其中一个processor save失败，咋办啊
        try {
            logService().save(t);
        } catch (Exception ex) {
            log.warn("processor:{} 持久化 lc:{} 异常:", getName(), t.getRequestId(), ex);
            //感觉没啥用，暂时先这样
            throw new IronException();
        }

        /// todo 异步提交
        submit(t);
    }

    /**
     * 提交到队列(重做不走 commit，直接走submit)
     *
     * todo 队列满异常测试
     *
     * @param t
     */
    @Override
    public void submit(T t) {
        /// 再提交队列
        try {
            queueService().submit(t);
        } catch (Exception ex) {
            // 入队失败
            log.warn("processor:{} submit lc:{} 异常:e", getName(), t.getRequestId(), ex);
        }
    }

    /**
     * 重做回调入口(实际工作入口)
     *
     * @param t 事件
     */
    @Override
    public void process(T t) {
        log.debug("t:{}", t);

        // first
        if (null != getFirstInnerEventHandler()) {
            getFirstInnerEventHandler().handle(t);
        }

        for (IHandler<T> handler : getHandlers()) {

            // 不关心
            if (!handler.care(t.getEventType())) {
                continue;
            }

            if (t.isRedoEvent()) {
                onRedoEvent(t, handler);
            } else {
                onFreshEvent(t, handler);
            }
        }

        /// last todo 异常处理（可能死循环）
        getLastInnerEventHandler().handle(t);
    }

    /**
     * 处理新鲜事件
     *
     * @return void
     */
    private void onFreshEvent(T t, IHandler<T> handler) {
        long start = System.currentTimeMillis();

        try {
            /// 处理成功
            if (handler.handle(t)) {
                log.debug("{} handle ec:{} 成功, cost:{}",
                        handler.getName(), t.getId(), System.currentTimeMillis() - start);
            }
            /// 处理失败
            else {
                log.info("{} handle ec:{} 失败", handler.getName(), t.getId());
                logService().saveRedo(t, handler);
            }
        } catch (Exception e) {
            log.warn("handler {} handle ec:{} 异常:", handler.getName(), t.getId(), e);
        }
    }

    /**
     * 重做消息统一处理方法
     *
     * @param t 事件
     * @param handler 处理器
     * @return void
     */
    private void onRedoEvent(T t, IHandler<T> handler) {
        // 重做不包含当前handler
        if (!t.getRedoHandlerIds().contains(handler.getId())) {
            return;
        }

        try {
            /// 重做成功
            if (handler.handle(t)) {
                /// 删除ec中的失败标记
                t.cleanRedoFlag(handler);

                log.info("{} reHandle ec:{} 成功", handler.getName(), t);

                logService().removeRedo(t);
            }
            /// 重做失败
            else {
                /// 打印日志，啥也不做
                log.info("{} reHandle ec:{} 失败", handler.getName(), t.getId());
            }
        } catch (Exception e) {
            log.debug("handler {} reHandle ec:{} 异常:", handler.getName(), t.getId(), e);
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
        }
    }


}
