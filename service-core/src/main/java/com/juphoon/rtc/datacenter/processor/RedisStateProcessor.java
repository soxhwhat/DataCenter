package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.STATE_BIN_LOG_IMPL_NONE;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/3 16:03
 * @Description:
 * TODO TODO 修改为status
 */
@Getter
@Component
public class RedisStateProcessor extends AbstractStateProcessor {
    @Autowired
    @Qualifier(STATE_BIN_LOG_IMPL_NONE)
    private ILogService<StateContext> logService;

    @Override
    public ILogService<StateContext> logService() {
        return logService;
    }

    @Override
    public ProcessorId processorId() {
        return ProcessorId.REDIS_STATE_PROCESSOR;
    }
}
