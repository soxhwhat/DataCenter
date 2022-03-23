package com.juphoon.rtc.datacenter.mq;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.mq.mapper.ILogMapper;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import com.juphoon.rtc.datacenter.handler.IEventHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public class EventQueueService extends AbstractEventQueueService {

    protected IEventQueue eventQueue;

    protected ILogMapper logMapper;

    /**
     * with default property
     */
    public EventQueueService(AbstractEventProcessor processor) {
        this(new EventQueueConfig(),processor);
    }

    /**
     * spec property
     *
     * @param config
     */
    public EventQueueService(EventQueueConfig config,AbstractEventProcessor processor) {
        // todo 后期添加动态加载logMapper  支持不同的mq支持不同的log数据源
        this.processor = processor;
//        this.logMapper = SpringBeanUtils.getBean(ILogMapper.class);
        DisruptorEventQueue queue = new DisruptorEventQueue();
        queue.init(config , this);
        this.eventQueue = queue;
    }

    /**
     * 提交事件
     *
     * @param ec
     * @return
     * @throws Exception
     */
    @Override
    public void submit(EventContext ec) {
        // TODO
        // 1. 数据落地
        // 2. 有界内存队列
        // 3. 驱动线程池消费队列
        log.info("ec:{}", ec);
//        logMapper.insertEvent(ec);
        try {
            eventQueue.push(ec);
        } catch (Exception e) {
            //TODO 此时一般为内存队列溢出 直接写入重做日志  后续最好加入熔断
            redo(ec);

        }
    }

    /// TODO
    /// 线程池消费事件   回调到 process 开启消费流程
    /// processor.handle(ec);

    @Override
    public void redo(EventContext ec) {
        //如果已经重做过了 说明已存在数据库中 则不写入重做记录中 避免数据重复
        if (ec.getRetryCount() < 0) {
            List<IEventHandler> eventHandlers = processor.getEventHandlers();
            for (IEventHandler handle : eventHandlers) {
//                logMapper.insertHandleData(ec, handle.getClass().getName());
            }
        }
    }

    @Override
    public void processOk(EventContext ec) {
//        logMapper.removeEvent(ec);
    }

    @Override
    public void redoOk(EventContext ec) {
//        logMapper.removeHandleData(ec);
    }
}