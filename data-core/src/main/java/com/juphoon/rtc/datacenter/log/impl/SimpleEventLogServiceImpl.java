package com.juphoon.rtc.datacenter.log.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.log.IEventLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * <p>简单事件日志</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    3/24/22 9:30 PM
 *
 * //@ConditionalOnProperty(prefix = JrtcDataCenterConstant.DATA_CENTER_CONFIG_PREFIX, name = "queue", havingValue = "sample")
 */
@Slf4j
@Component
public class SimpleEventLogServiceImpl implements IEventLogService {
    @Override
    public void saveEvent(EventContext ec) {
        log.info("saveElement:{}", ec);
    }

    @Override
    public void removeEvent(EventContext ec) {
        log.info("removeEvent:{}", ec);
    }

    @Override
    public void updateEventQueued(EventContext ec) {
        log.info("updateEventQueued:{}", ec);
    }

    @Override
    public IPage<EventContext> findUnqueuedEvents() {
        return null;
    }

    @Override
    public void startup() {

    }

    @Override
    public void initTables() {

    }
}
