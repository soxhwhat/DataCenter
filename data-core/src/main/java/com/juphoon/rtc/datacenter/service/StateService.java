package com.juphoon.rtc.datacenter.service;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.processor.IProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 17:59
 * @Description:
 */
@Slf4j
public class StateService {
    private List<IProcessor<StateContext>> processors;

    public StateService(List<IProcessor<StateContext>> processors) {
        assert null != processors : "processors 不能为空";

        this.processors = processors;
    }

    /**
     * 提交任务
     *
     * @param context
     * @throws Exception
     */
    public void commit(StateContext context) {
        assert null != context : "context 为空";

        for (IProcessor<StateContext> processor : processors) {
            log.debug("{} process ec:{}", processor.getId(), context.getRequestId());

            processor.commit(context);
        }
    }

    public void commit(List<StateContext> contexts) {
        log.debug("contexts:{}", contexts.size());
        contexts.forEach(this::commit);
    }
}
