package com.juphoon.rtc.datacenter.datacore.binlog.mapper.reliable;

import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.AbstractEventLogMapper;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_BASE_PATH;
import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_RELIABLE;

/**
 * @author ajian.zheng
 * @data 2022-05-12
 * @update [1][2022-09-07] [ajian.zheng][重写]
 */
@Component
public class ReliableEventLogMapper extends AbstractEventLogMapper {
    @Override
    public String dbPath() {
        return System.getProperty("user.dir") + LOCAL_DB_FILE_BASE_PATH + LOCAL_DB_FILE_RELIABLE;
    }
}
