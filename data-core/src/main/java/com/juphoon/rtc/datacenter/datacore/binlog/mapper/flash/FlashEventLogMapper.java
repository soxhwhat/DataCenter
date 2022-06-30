package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.binlog.mapper.EventLogMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author ajian.zheng
 * @data 2022-05-12
 */
@Mapper
@Component
public interface FlashEventLogMapper extends EventLogMapper {
}
