package com.juphoon.rtc.datacenter.mapper;

import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorAcdQueueCountPO;
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
public interface JrtcMonitorAcdQueueCountMapper {
    /**
     * 查询队列吸吸
     * @param queue
     * @return
     */
    int selectCountByQueue(String queue);

    /**
     * 插入记录
     *
     * @param po
     * @return
     */
    int insert(MonitorAcdQueueCountPO po);

    /**
     * 更新记录
     *
     * @param record
     * @return
     */
    int updateByQueueAndFrom(MonitorAcdQueueCountPO record);

}