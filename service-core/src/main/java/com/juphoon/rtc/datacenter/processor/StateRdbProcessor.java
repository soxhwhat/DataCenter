package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.processor.loader.AbstractContextLoader;
import com.juphoon.rtc.datacenter.processor.loader.ContextLoaderConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.STATE_BIN_LOG_IMPL_NONE;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 10:39
 * @update:
 * <p>1. 2022-03-22. ajian.zheng 增加处理器名</p>
 * <p>1. 2022-05-16. ajian.zheng 调整结构，eventLog/redoLog/eventQueueService改为设入，非自动装配</p>
 */
@Slf4j
@Getter
@Component
public class StateRdbProcessor extends AbstractStateProcessor {
    @Autowired
    @Qualifier(STATE_BIN_LOG_IMPL_NONE)
    private ILogService<StateContext> logService;

    @Override
    public ProcessorId processorId() {
        return ProcessorId.STATE_RDB;
    }

    @Override
    public ILogService<StateContext> logService() {
        return logService;
    }

    @Override
    public void buildContextLoader(ContextLoaderConfig config) {
        if (!config.isEnabled()) {
            return;
        }

        setContextLoader(new AbstractContextLoader<StateContext>(logService(), this, config) {
            @Override
            public List<StateContext> loadContexts(ILogService<StateContext> logService) {
                return logService.find(config.getLoadSize());
            }
        });
    }
}
