package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.handler.inner.LastInnerHandler;
import com.juphoon.rtc.datacenter.processor.queue.QueueServiceConfig;
import com.juphoon.rtc.datacenter.processor.queue.impl.DisruptorStateQueueServiceImpl;
import com.juphoon.rtc.datacenter.processor.queue.impl.NoneStateQueueServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.QUEUE_SERVICE_CONFIG_TYPE_DISRUPTOR;
import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.QUEUE_SERVICE_CONFIG_TYPE_NONE;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 10:39
 * @update:
 * <p>1. 2022-03-22. ajian.zheng 增加处理器名</p>
 * <p>1. 2022-05-16. ajian.zheng 调整结构，eventLog/redoLog/eventQueueService改为设入，非自动装配</p>
 */
@Slf4j
@Getter
public abstract class AbstractStateProcessor extends AbstractProcessor<StateContext> {
    @Override
    public LastInnerHandler<StateContext> lastInnerEventHandler() {
        return new LastInnerHandler<>(this);
    }

    @Override
    public void buildQueueService(QueueServiceConfig config) {
        switch (config.getType()) {
            case QUEUE_SERVICE_CONFIG_TYPE_DISRUPTOR:
                setQueueService(new DisruptorStateQueueServiceImpl(this, config));
                break;
            case QUEUE_SERVICE_CONFIG_TYPE_NONE:
                setQueueService(new NoneStateQueueServiceImpl(this, config));
                break;
            default:
                throw new IllegalArgumentException("无效的 QueueService 类型:" + config.getType() + "," + getId());
        }
    }
}
