package com.juphoon.rtc.datacenter.service;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.processor.IProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 17:59
 * @Description:
 */
@Slf4j
public class EventService {
    private List<IProcessor<EventContext>> processors;

    public EventService(List<IProcessor<EventContext>> processors) {
        assert null != processors : "processors 不能为空";

        this.processors = processors;
    }

    /**
     * 提交任务
     *
     * @param ec
     * @throws Exception
     */
    public void commit(EventContext ec) {
        assert null != ec : "ec为空";

        for (IProcessor<EventContext> processor : processors) {
            log.debug("{} process ec:{},{}", processor.getId(), ec.getEventType(), ec.getRequestId());

            processor.commit(ec);
        }
    }

    /**
     * 批量提交
     *
     * @param contexts
     */
    public void commit(List<EventContext> contexts) {
        log.debug("contexts:{}", contexts.size());
        contexts.forEach(this::commit);
    }

}