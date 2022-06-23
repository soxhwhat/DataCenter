package com.juphoon.rtc.datacenter.mapper;

import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorRoomConcurrentPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>jrtc_monitor_acd_queue 表的mapper类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @since 2022/6/21 15:18
 */
@Mapper
@Component
public interface MonitorRoomConcurrentMapper {
    /**
     * 查询总并发量
     * @param timestamp 有效时间范围
     * @return
     */
    MonitorRoomConcurrentPO selectConcurrent(long timestamp);

    /**
     * 插入记录
     *
     * @param po
     * @return
     */
    int insert(MonitorRoomConcurrentPO po);

    /**
     * 更新记录
     *
     * @param record
     * @return
     */
    int updateByFromAndDomainIdAndAppId(MonitorRoomConcurrentPO record);
}