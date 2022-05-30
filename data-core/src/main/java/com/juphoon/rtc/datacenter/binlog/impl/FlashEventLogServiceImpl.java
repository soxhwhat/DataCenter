package com.juphoon.rtc.datacenter.binlog.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.handler.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;

/**
 * <p>快速的</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    5/31/22 5:43 PM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component(EVENT_BIN_LOG_IMPL_FLASH)
public class FlashEventLogServiceImpl implements ILogService<EventContext> {
    @Override
    public void save(EventContext eventContext) {

    }

    @Override
    public void remove(EventContext eventContext) {

    }

    @Override
    public void saveRedo(EventContext eventContext, IHandler<EventContext> handler) {

    }

    @Override
    public void removeRedo(EventContext eventContext) {

    }
}
