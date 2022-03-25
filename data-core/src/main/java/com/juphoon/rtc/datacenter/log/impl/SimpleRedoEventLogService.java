package com.juphoon.rtc.datacenter.log.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.handler.IEventHandler;
import com.juphoon.rtc.datacenter.log.IRedoEventLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * <p>简单重做事件日志</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    3/24/22 9:30 PM
 */
@Slf4j
@Component
//@ConditionalOnProperty(prefix = JrtcDataCenterConstant.DATA_CENTER_CONFIG_PREFIX, name = "queue", havingValue = "sample")
public class SimpleRedoEventLogService implements IRedoEventLogService {
    @Override
    public void saveRedoEvent(EventContext ec, IEventHandler handler) {
        log.info("saveRedoEvent ec:{},handler:{}", ec, handler.getName());
    }

    @Override
    public void removeRedoEvent(EventContext ec, IEventHandler handler) {
        log.info("removeRedoEvent ec:{},handler:{}", ec, handler.getName());
    }

    @Override
    public void initTables() {

    }
}
