package com.juphoon.rtc.datacenter.event.storage.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.binlog.BasicEventLogService;
import com.juphoon.rtc.datacenter.handler.IHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>简单事件日志</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/24/22 9:30 PM
 *
 */
@Slf4j
public class NoneEventLogServiceImpl extends BasicEventLogService {
    @Override
    public void save(EventContext eventContext) {
        log.info("ec:{}", eventContext);
    }

    @Override
    public void remove(EventContext eventContext) {
        log.info("ec:{}", eventContext);
    }

    @Override
    public void saveRedo(EventContext eventContext, IHandler<EventContext> handler) {
        log.info("ec:{},handler:{}", eventContext, handler.getId());

    }

    @Override
    public void removeRedo(EventContext eventContext) {
        log.info("ec:{}", eventContext);
    }
}
