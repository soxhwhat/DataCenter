package com.juphoon.rtc.datacenter.processor.queue;

import com.google.common.collect.Sets;
import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.exception.JrtcRepeatedSubmitEventException;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import com.juphoon.rtc.datacenter.processor.AbstractLogProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public abstract class AbstractLogQueueService implements IQueueService<LogContext> {
    public AbstractLogQueueService(AbstractLogProcessor processor, QueueServiceConfig config) {
        this.processor = processor;
        init(config);
    }

    private AbstractLogProcessor processor;

    private Set<String> eventIndex = Sets.newConcurrentHashSet();

    @Override
    public void init(QueueServiceConfig config) {
    }

    /**
     * 获取处理器
     */
    public AbstractLogProcessor getProcessor() {
        return processor;
    }

    /**
     * 处理事件提交
     *
     * @param ec
     * @throws Exception
     */
    public abstract void onSubmit(LogContext ec) throws Exception;

    @Override
    public void submit(LogContext ec) throws Exception {
        log.debug("ec:{}", ec);

        // 去重
        if (eventIndex.contains(ec.getId())) {
            throw new JrtcRepeatedSubmitEventException(ec.getId() + " 重复提交");
        }

        eventIndex.add(ec.getId());

        onSubmit(ec);
    }

    @Override
    public void success(LogContext ec) {
        log.debug("ec:{},{}", ec, eventIndex.size());

        eventIndex.remove(ec.getId());
    }
}