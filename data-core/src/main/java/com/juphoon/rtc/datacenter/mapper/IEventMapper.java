package com.juphoon.rtc.datacenter.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juphoon.rtc.datacenter.mq.entity.EventPO;
import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhiwei.zhai
 * @date 2022-03-16
 */
@Mapper
@DS("log")
public interface IEventMapper extends BaseMapper<EventPO> {

    /**
     * 初始化表
     */
    void initEventTable();

    /**
     * 更新当前事件为就绪状态
     * @param eventId
     */
    default void updateEventReady(String eventId){
        EventPO eventPO = new EventPO();
        eventPO.setId(eventId);
        eventPO.setState(JrtcDataCenterConstant.STATE_READYING);
        this.updateById(eventPO);
    };
}
