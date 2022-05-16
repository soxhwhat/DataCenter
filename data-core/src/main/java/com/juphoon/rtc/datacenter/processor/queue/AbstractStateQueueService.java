package com.juphoon.rtc.datacenter.processor.queue;

import com.google.common.collect.Sets;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.exception.JrtcRepeatedSubmitEventException;
import com.juphoon.rtc.datacenter.processor.AbstractStateProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public abstract class AbstractStateQueueService implements IQueueService<StateContext> {
    public AbstractStateQueueService(AbstractStateProcessor processor, QueueServiceConfig config) {
        this.processor = processor;
        init(config);
    }

    private AbstractStateProcessor processor;

    private Set<Long> eventIndex = Sets.newConcurrentHashSet();

    /**
     * 获取处理器
     */
    public AbstractStateProcessor getProcessor() {
        return processor;
    }

    /**
     * 处理事件提交
     *
     * @param ec
     * @throws Exception
     */
    public abstract void onSubmit(StateContext ec) throws Exception;

    @Override
    public void submit(StateContext ec) throws Exception {
        log.debug("ec:{}", ec);

        // 去重
        if (eventIndex.contains(ec.getId())) {
            throw new JrtcRepeatedSubmitEventException(ec.getId() + " 重复提交");
        }

        eventIndex.add(ec.getId());

        onSubmit(ec);
    }

    /// TODO 可以收敛到抽象类中
    @Override
    public void success(StateContext ec) {
        log.debug("ec:{},{}", ec, eventIndex.size());

        eventIndex.remove(ec.getId());
    }
}