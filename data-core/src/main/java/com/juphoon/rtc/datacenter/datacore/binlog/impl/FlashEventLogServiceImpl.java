package com.juphoon.rtc.datacenter.datacore.binlog.impl;

import com.juphoon.rtc.datacenter.datacore.binlog.mapper.EventLogMapper;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FlashEventLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.*;

/**
 * <p>FlashEventLogServiceImpl 实现类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/31/22 5:43 PM
 */
@Slf4j
@Component(EVENT_BIN_LOG_IMPL_FLASH)
public class FlashEventLogServiceImpl extends AbstractEventLogService {
    @Autowired
    private FlashEventLogMapper logMapper;

    @Override
    public EventLogMapper getEventLogMapper() {
        return logMapper;
    }

    @Override
    public String dbFileName() {
        return LOCAL_DB_FILE_BASE_PATH + LOCAL_DB_FILE_FLASH;
    }
}
