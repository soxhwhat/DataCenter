package com.juphoon.rtc.datacenter.binlog.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.handler.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.STATE_BIN_LOG_IMPL_NONE;

/**
 * <p>快速的</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/31/22 5:43 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component(STATE_BIN_LOG_IMPL_NONE)
public class NoneStateLogServiceImpl implements ILogService<StateContext> {
    @Override
    public void save(StateContext context) {
        log.info("context:{}", context);
    }

    @Override
    public void save(StateContext context, IHandler handler) {
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
    public List<StateContext> find(int size) {
        log.info("size:{}", size);
        return null;
    }
}
