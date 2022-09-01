package com.juphoon.rtc.datacenter.datacore.binlog.impl;

import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.handler.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.STATE_BIN_LOG_IMPL_NONE;

/**
 * <p>NoneStateLogServiceImpl 实现类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/31/22 5:43 PM
 */
@Slf4j
@Component(STATE_BIN_LOG_IMPL_NONE)
public class NoneStateLogServiceImpl implements ILogService<StateContext> {
    @Override
    public void save(StateContext context) {
        log.info("context:{}", context);
    }

    @Override
    public void saveRedo(StateContext context, IHandler handler) {
        log.info("context:{},{}", context, handler.getId());
    }

    @Override
    public void save(List<StateContext> list) {
        log.info("list:{}", list.size());
    }

    @Override
    public void remove(StateContext context) {
        log.info("context:{}", context);
    }

    @Override
    public void remove(Long id) {
        log.info("id:{}", id);
    }

    @Override
    public List<StateContext> find(int size) {
        log.info("size:{}", size);
        return null;
    }

    @Override
    public void remove(List<Long> ids) {
    }

    @Override
    public void updateRetryCount(StateContext context) {
        log.info("context:{}", context);
    }
}
