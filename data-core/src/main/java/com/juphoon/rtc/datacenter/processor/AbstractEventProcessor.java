package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.ICare;
import com.juphoon.rtc.datacenter.handler.inner.FirstInnerEventHandler;
import com.juphoon.rtc.datacenter.handler.AbstractCareAllEventHandler;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import com.juphoon.rtc.datacenter.handler.IEventHandler;
import com.juphoon.rtc.datacenter.handler.inner.LastInnerEventHandler;
import com.juphoon.rtc.datacenter.mq.IEventQueueService;
import com.juphoon.rtc.datacenter.processor.IEventProcessor;
import com.juphoon.rtc.datacenter.utils.SnowFlakeGenerateIdWorker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 10:39
 * @update:
 * <p>1. 2022-03-22. ajian.zheng 增加处理器名</p>
 */
@EqualsAndHashCode
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
    Set<EventType> careEvents = new HashSet<>();

    /**
     * 处理器名
     */
    private String name = this.toString();

    /**
     * 是否启用
     */
    private boolean enabled = true;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 设置处理器名
     *
     * @param name
     */
    public void setProcessorName(String name) {
        this.name = name;
    }

    private static SnowFlakeGenerateIdWorker snowFlakeGenerateIdWorker = new SnowFlakeGenerateIdWorker(5L, 5L);

    /**
     * 设置队列服务
     *
     * @param queueService
     */
    public void setQueueService(IEventQueueService queueService) {
        this.queueService = queueService;
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
     * @param handler
     */
    public synchronized void addEventHandler(IEventHandler handler) {
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

    /**
     * 添加处理句柄
     *
     * @param handlers
     */
    public void addEventHandlers(List<AbstractEventHandler> handlers) {
        assert null != handlers : "handlers 不能为空";

        for (IEventHandler handler : handlers) {
            addEventHandler(handler);
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
        //TODO 如果是重做的情况需要判断当前process是否仍然需要消费该数据
        if (!StringUtils.isEmpty(ec.getProcessClzName()) && !this.getClass().getName().equals(ec.getProcessClzName())) {
            return;
        }
        if (care(ec.getEvent()) || isAllCare) {
            if (StringUtils.isEmpty(ec.getId())) {
                ec.setId(snowFlakeGenerateIdWorker.generateNextId());
                ec.setProcessClzName(this.getClass().getName());
            }
            queueService.submit(ec);
        }
    }

}
