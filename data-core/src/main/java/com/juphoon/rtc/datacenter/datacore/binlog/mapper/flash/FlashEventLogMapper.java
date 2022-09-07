package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_BASE_PATH;
import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_FLASH;

/**
 * @author ajian.zheng
 * @data 2022-05-12
 */
@Component
public class FlashEventLogMapper extends AbstractEventLogMapper {
    @Override
    public String dbPath() {
        return System.getProperty("user.dir") + LOCAL_DB_FILE_BASE_PATH + LOCAL_DB_FILE_FLASH;
    }
}
