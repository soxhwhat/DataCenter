package com.juphoon.rtc.datacenter.datacore.service;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.processor.IProcessor;
import com.juphoon.rtc.datacenter.datacore.utils.MetricUtils;
import io.micrometer.core.instrument.Timer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 17:59
 * @Description:
 */
@Slf4j
@Getter
public class EventService {
    private List<IProcessor<EventContext>> processors;

    public EventService(List<IProcessor<EventContext>> processors) {
        assert null != processors : "processors 不能为空";

        this.processors = processors;
    }

    /**
     * 提交任务
     *
     * @param context
     */
    public void commit(EventContext context) {
        assert null != context : "context 为空";

        for (IProcessor<EventContext> processor : processors) {
            log.debug("{} process ec:{},{}", processor.getId(), context.getEventType(), context.getRequestId());

            processor.commit(context);
        }
    }

    /**
     * 批量提交
     *
     * @param contexts
     */
    public void commit(List<EventContext> contexts) {
        log.debug("contexts:{}", contexts.size());

        Timer.Sample sample = Timer.start();
        contexts.forEach(this::commit);
        sample.stop(MetricUtils.get("EventService.commit"));
    }

}
