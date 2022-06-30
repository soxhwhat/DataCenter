package com.juphoon.rtc.datacenter.dist.b03.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Yuan wenjun
 * @date 2022/04/28
 */
@Data
public class EventMapPO {
    public long timestamp;
    public int type;
    public int eventNumber;
    public String uuid;
    public List<Object> tags;
    public Map<String, Object> params;
    public long domainId;
    public long appId;
}
