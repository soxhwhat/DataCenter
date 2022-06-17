package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.handler.inner.LastInnerHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.LOG_BIN_LOG_IMPL_FLASH;

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
public class MongoLogProcessor extends AbstractLogProcessor {
    @Autowired
    @Qualifier(LOG_BIN_LOG_IMPL_FLASH)
    private ILogService<LogContext> logService;

    @Override
    public ProcessorId processorId() {
        return ProcessorId.EXT_LOG;
    }

    @Override
    public ILogService<LogContext> logService() {
        return logService;
    }
}