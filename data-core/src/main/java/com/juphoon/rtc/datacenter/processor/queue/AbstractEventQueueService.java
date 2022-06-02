package com.juphoon.rtc.datacenter.processor.queue;

import com.google.common.collect.Sets;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.exception.JrtcRepeatedSubmitEventException;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public abstract class AbstractEventQueueService implements IQueueService<EventContext> {
    public AbstractEventQueueService(AbstractEventProcessor processor, QueueServiceConfig config) {
        this.processor = processor;
        init(config);
    }

    private AbstractEventProcessor processor;

    private Set<String> eventIndex = Sets.newConcurrentHashSet();

    /**
     * 获取处理器
     */
    public AbstractEventProcessor getProcessor() {
        return processor;
    }

    /**
     * 处理事件提交
     *
     * @param ec
     * @throws Exception
     */
    public abstract void onSubmit(EventContext ec) throws Exception;

    @Override
    public void submit(EventContext ec) throws Exception {
        log.debug("ec:{}", ec);

        // 去重
        if (eventIndex.contains(ec.getId())) {
            throw new JrtcRepeatedSubmitEventException(ec.getId() + " 重复提交");
        }

        eventIndex.add(ec.getId());

        onSubmit(ec);
    }

    @Override
    public void success(EventContext ec) {
        log.debug("ec:{},{}", ec, eventIndex.size());

        eventIndex.remove(ec.getId());
    }
}