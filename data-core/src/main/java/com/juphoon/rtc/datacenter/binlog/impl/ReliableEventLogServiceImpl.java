package com.juphoon.rtc.datacenter.binlog.impl;

import com.juphoon.rtc.datacenter.binlog.mapper.EventLogMapper;
import com.juphoon.rtc.datacenter.binlog.mapper.reliable.ReliableEventLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_RELIABLE;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
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
}
