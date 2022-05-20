package com.juphoon.rtc.datacenter.event.storage.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.event.queue.IEventQueueService;
import com.juphoon.rtc.datacenter.event.storage.AbstractEventLogService;
import com.juphoon.rtc.datacenter.handler.IEventHandler;
import com.juphoon.rtc.datacenter.processor.IEventProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>简单事件日志</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/24/22 9:30 PM
 *
 */
@Slf4j
public class NoneEventLogServiceImpl extends AbstractEventLogService {
    @Override
    public void saveEventLog(EventContext ec, IEventProcessor processor) {
        log.debug("ec:{}", ec);
    }

    @Override
    public void removeEventLog(EventContext ec, IEventProcessor processor) {
        log.debug("ec:{}", ec);
    }

    @Override
    public void saveRedoLog(EventContext ec, IEventHandler handler) {
        log.debug("ec:{}", ec);
    }

    @Override
    public void removeRedoLog(EventContext ec) {
        log.debug("ec:{}", ec);
    }

    @Override
    public void startup() {

    }

    @Override
    public void initTables() {

    }
}
