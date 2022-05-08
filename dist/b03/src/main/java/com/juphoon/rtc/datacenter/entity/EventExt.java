package com.juphoon.rtc.datacenter.entity;

import com.juphoon.rtc.datacenter.api.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/5/6 18:40
 * @Description:
 */
@Getter
@Setter
public class EventExt{
    /**
     * 关联事件
     */
    private List<EventMapPO> ext;

    private Map<String,Object> params;

    private long timestamp;
    private int type;
    private int eventNumber;
    private String uuid;
    private java.util.List<String > tags;
    private long domainId;
    private long appId;
}
