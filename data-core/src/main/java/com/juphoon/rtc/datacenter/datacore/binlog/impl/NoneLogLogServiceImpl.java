package com.juphoon.rtc.datacenter.datacore.binlog.impl;

import com.juphoon.rtc.datacenter.datacore.api.LogContext;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.handler.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOG_BIN_LOG_IMPL_NONE;

/**
 * <p>NoneLogLogServiceImpl 实现类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/31/22 5:43 PM
 */
@Slf4j
@Component(LOG_BIN_LOG_IMPL_NONE)
public class NoneLogLogServiceImpl implements ILogService<LogContext> {
    @Override
    public void save(LogContext context) {
        log.info("context:{}", context);
    }

    @Override
    public void saveRedo(LogContext context, IHandler handler) {
        log.info("context:{},{}", context, handler.getId());
    }

    @Override
    public void save(List<LogContext> list) {
        log.info("list:{}", list.size());
    }

    @Override
    public void remove(LogContext context) {
        log.info("context:{}", context);
    }

    @Override
    public List<LogContext> find(int size) {
        log.info("size:{}", size);
        return null;
    }

    @Override
    public void updateRetryCount(LogContext context) {
        log.info("context:{}", context);
    }
}
