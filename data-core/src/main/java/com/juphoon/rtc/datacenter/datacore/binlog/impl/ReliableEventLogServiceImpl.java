package com.juphoon.rtc.datacenter.datacore.binlog.impl;

import com.juphoon.rtc.datacenter.datacore.binlog.mapper.EventLogMapper;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.reliable.ReliableEventLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.*;

/**
 * <p>ReliableEventLogServiceImpl 实现类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/31/22 5:43 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component(EVENT_BIN_LOG_IMPL_RELIABLE)
public class ReliableEventLogServiceImpl extends AbstractEventLogService {
    @Autowired
    private ReliableEventLogMapper logMapper;

    @Override
    public EventLogMapper getEventLogMapper() {
        return logMapper;
    }

    @Override
    public String dbFileName() {
        return LOCAL_DB_FILE_BASE_PATH + LOCAL_DB_FILE_RELIABLE;
    }
}
