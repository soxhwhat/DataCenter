package com.juphoon.rtc.datacenter.datacore.processor;

import com.juphoon.iron.component.utils.response.IronException;
import com.juphoon.rtc.datacenter.datacore.api.BaseContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.ICare;
import com.juphoon.rtc.datacenter.datacore.handler.IHandler;
import com.juphoon.rtc.datacenter.datacore.handler.inner.FirstInnerHandler;
import com.juphoon.rtc.datacenter.datacore.handler.inner.LastInnerHandler;
import com.juphoon.rtc.datacenter.datacore.processor.loader.AbstractContextLoader;
import com.juphoon.rtc.datacenter.datacore.processor.queue.IQueueService;
import com.juphoon.rtc.datacenter.datacore.utils.MetricUtils;
import io.micrometer.core.instrument.Timer;
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
public abstract class AbstractProcessor<T extends BaseContext> implements IProcessor<T>, ICare {
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

    /**
     * 事件加载器
     */
    private AbstractContextLoader<T> contextLoader;

    public void setQueueService(IQueueService<T> queueService) {
        this.queueService = queueService;
    }

    public void setContextLoader(AbstractContextLoader<T> contextLoader) {
        this.contextLoader = contextLoader;
    }

    @Override
    public IQueueService<T> queueService() {
        assert null != queueService : "队列服务未初始化";
        return queueService;
    }

    @Override
    public AbstractContextLoader<T> getContextLoader() {
        return contextLoader;
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
        T clone = (T) t.clone();
        clone.setProcessorId(this.processorId().getId());

        /// 先入库
        /// TODO 若其中一个processor save失败，咋办啊
        Timer.Sample sample = Timer.start();
        try {
            logService().save(clone);
        } catch (Exception ex) {
            log.warn("processor:{} 持久化 lc:{} 异常:", getName(), clone.getRequestId(), ex);
            //感觉没啥用，暂时先这样
            throw new IronException();
        }
        sample.stop(MetricUtils.get("logService.save()"));

        /// todo 异步提交
        Timer.Sample sample2 = Timer.start();
        submit(clone);
        sample2.stop(MetricUtils.get("queueService.submit()"));
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

        // 开始处理
        t.handle();

        // first
        if (null != getFirstInnerEventHandler()) {
            Timer.Sample sample = Timer.start();
            getFirstInnerEventHandler().handle(t);
            sample.stop(MetricUtils.get("process.firstHandler"));
        }

        Timer.Sample sample2 = Timer.start();
        for (IHandler<T> handler : getHandlers()) {

            // 不关心
            if (!handler.care(t.getEventType())) {
                continue;
            }

            /// 重做句柄ID匹配才执行重做
            /// 重做消息只针对一个handler，无需继续传递
            if (t.isRedoEvent()) {
                if (handler.getId().equals(t.getRedoHandler())) {
                    onRedoEvent(t, handler);
                    break;
                }
            } else {
                onFreshEvent(t, handler);
            }
        }
        sample2.stop(MetricUtils.get("process.handlers"));

        /// last todo 异常处理（可能死循环）
//        if (!t.isRedoEvent()) {
        Timer.Sample sample3 = Timer.start();
        getLastInnerEventHandler().handle(t);
        sample3.stop(MetricUtils.get("process.lastHandler"));
//        }
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
                log.debug("{} handle ec:{} 失败", handler.getName(), t.getId());

                logService().saveRedo(t, handler);
            }
        } catch (Exception e) {
            logService().saveRedo(t, handler);

            /// TODO 严重
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
        try {
            /// 重做成功
            if (handler.handle(t)) {
                log.debug("{} reHandle ec:{} 成功", handler.getName(), t);
                t.redoOK();
                return;
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
        }

        // 重做失败
        // 更新失败次数(可选逻辑)
        logService().updateRetryCount(t);

        /// 打印日志，啥也不做
        log.debug("{} redo ec:{} 失败", handler.getName(), t.getId());
    }

    @Override
    public void start() {
        logService().start();

        queueService().start();

        if (null != contextLoader) {
            contextLoader.start();
        }
    }
}
