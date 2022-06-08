package com.juphoon.rtc.datacenter.binlog.mapper.reliable;

import com.juphoon.rtc.datacenter.binlog.mapper.EventLogMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author ajian.zheng
 * @data 2022-05-12
 */
@Mapper
@Component
public interface ReliableEventLogMapper extends EventLogMapper {
}
