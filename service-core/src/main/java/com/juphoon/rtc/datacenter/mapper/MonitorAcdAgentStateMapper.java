package com.juphoon.rtc.datacenter.mapper;

import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorAcdAgentStatePO;
import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorAcdQueueCountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>jrtc_monitor_acd_agent_state 表的mapper类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @since 2022/6/21 16:18
 */
@Mapper
@Component
public interface MonitorAcdAgentStateMapper {
    /**
     * 查询坐席状态
     * @param agent
     * @return
     */
    MonitorAcdAgentStatePO selectByAgentId(String agent);

    /**
     * 插入记录
     *
     * @param po
     * @return
     */
    int insert(MonitorAcdAgentStatePO po);

    /**
     * 更新记录
     *
     * @param po
     * @return
     */
    int updateStateByAgentId(MonitorAcdAgentStatePO po);


    /**
     * 更新签出事件
     *
     * @param po
     * @return
     */
    int updateCheckOutByAgentId(MonitorAcdAgentStatePO po);
}