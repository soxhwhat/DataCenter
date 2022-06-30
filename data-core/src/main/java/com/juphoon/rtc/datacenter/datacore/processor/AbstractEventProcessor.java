package com.juphoon.rtc.datacenter.datacore.processor;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.handler.inner.LastInnerHandler;
import com.juphoon.rtc.datacenter.datacore.processor.loader.AbstractContextLoader;
import com.juphoon.rtc.datacenter.datacore.processor.loader.ContextLoaderConfig;
import com.juphoon.rtc.datacenter.datacore.processor.queue.QueueServiceConfig;
import com.juphoon.rtc.datacenter.datacore.processor.queue.impl.NoneEventQueueServiceImpl;
import com.juphoon.rtc.datacenter.datacore.processor.queue.impl.ExecutorEventQueueServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.QUEUE_SERVICE_CONFIG_TYPE_EXECUTOR;
import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.QUEUE_SERVICE_CONFIG_TYPE_NONE;


/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 10:39
 * @update:
 * <p>1. 2022-03-22. ajian.zheng 增加处理器名</p>
 * <p>2. 2022-05-16. ajian.zheng 调整结构，eventLog/redoLog/eventQueueService改为设入，非自动装配</p>
 * <p>3. 2022-05-31. ajian.zheng 魔改</p>
 * <p>4. 2022-07-11. ajian.zheng executor 替代 disruptor </p>
 */
@Slf4j
@Getter
public abstract class AbstractEventProcessor extends AbstractProcessor<EventContext> {
    @Override
    public LastInnerHandler<EventContext> lastInnerEventHandler() {
        return new LastInnerHandler<>(this);
    }

    @Override
    public void buildQueueService(QueueServiceConfig config) {
        switch (config.getType()) {
            case QUEUE_SERVICE_CONFIG_TYPE_EXECUTOR:
                setQueueService(new ExecutorEventQueueServiceImpl(this, config));
                break;
            case QUEUE_SERVICE_CONFIG_TYPE_NONE:
                setQueueService(new NoneEventQueueServiceImpl(this, config));
                break;
            default:
                throw new IllegalArgumentException("无效的 QueueService 类型:" + config.getType() + "," + getId());
        }
    }

    @Override
    public void buildContextLoader(ContextLoaderConfig config) {
        if (!config.isEnabled()) {
            return;
        }

        setContextLoader(new AbstractContextLoader<EventContext>(logService(), this, config) {
            @Override
            public List<EventContext> loadContexts(ILogService<EventContext> logService) {
                return logService.find(config.getLoadSize());
            }
        });
    }
}
