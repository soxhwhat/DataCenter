package com.juphoon.rtc.datacenter.log.sqllite.mapper;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhiwei.zhai
 * @data 2022-02-24
 */
@Mapper
@DS("log")
public interface ILogMapper {

    /**
     * 初始化表
     */
    void initEventTable();

    /**
     * 初始化表
     */
    void initHandleDataTable();

    /**
     * 插入event事件数据
     * @param eventContext
     */
    void insertEvent(EventContext eventContext);

    /**
     * 删除event事件数据
     * @param eventContext
     */
    void removeEvent(EventContext eventContext);

    /**
     * 插入handle数据
     *
     * @param eventContext
     * @param eventHandlerName
     */
    void insertHandleData(EventContext eventContext, String eventHandlerName);

    /**
     * 删除handle数据
     * @param eventContext
     */
    void removeHandleData(EventContext eventContext);
}
