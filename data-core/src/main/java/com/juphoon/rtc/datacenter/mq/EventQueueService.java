package com.juphoon.rtc.datacenter.mq;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.RedoPO;
import com.juphoon.rtc.datacenter.mq.mapper.ILogMapper;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import com.juphoon.rtc.datacenter.handler.IEventHandler;
import com.juphoon.rtc.datacenter.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public class EventQueueService extends AbstractEventQueueService {

    protected IEventQueue eventQueue;

    protected LogService logService;

    /**
     * with default property
     */
    public EventQueueService(AbstractEventProcessor processor,LogService logService) {
        this(new EventQueueConfig(),processor,logService);
    }

    /**
     * spec property
     *
     * @param config
     */
    public EventQueueService(EventQueueConfig config, AbstractEventProcessor processor, LogService logService) {
        // todo 后期添加动态加载logMapper  支持不同的mq支持不同的log数据源
        this.processor = processor;
        this.logService = logService;
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
        ec.handle();
        log.info("ec:{}", ec);
        boolean pushSuccess = true;
        try {
            eventQueue.push(ec);
        } catch (Exception e) {
            pushSuccess = false;
        }
        try {
            if (ec.getRetryCount() == 0) {
                logService.eventBeginLog(ec, pushSuccess);
            } else {
                logService.updateEventRetry(ec);
            }
        } catch (Exception e) {
            log.error("插入事件开始日志错误:{}" , e);
        }
    }

    @Override
    public void redo(EventContext ec, String handleName) {
        //如果已经重做过了 说明已存在数据库中 则不写入重做记录中 避免数据重复
        log.info("redo方法执行中:{}" ,ec);
        if (ec.getRetryCount() == 0) {
            String redoId = logService.handleFailLog(ec, handleName);
            ec.getRedoClzMap().put(handleName,redoId);
        }
    }

    /**
     * 1、判断重做日志中是否存在
     * @param ec
     */
    @Override
    public void processOk(EventContext ec) {
        List<RedoPO> redoDataByEventId = logService.getRedoDataByEventId(ec.getId());
        if (CollectionUtils.isEmpty(redoDataByEventId)){
            logService.allSuccess(ec.getId());
        } else {
            logService.updateEventReady(ec.getId());
        }
    }

    @Override
    public void redoOk(String redoId) {
        logService.redoSuccess(redoId);
    }
}