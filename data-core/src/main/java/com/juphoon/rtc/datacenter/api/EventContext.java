package com.juphoon.rtc.datacenter.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Getter
@Setter
@ToString
public class EventContext {

    /**
     * 消息id
     */
    private String id;

    /**
     * 消息体
     */
    private Event event;

    /**
     * key为重做的handle名称   value为redoId
     * 重做handle的Class列表
     */
    private Map<String,String> redoClzMap = new HashMap<>();

    /**
     * process的类名
     */
    private String processClzName;

    /**
     * 创建时间
     */
    private long createdTimestamp = System.currentTimeMillis();

    /**
     * 首次消费时间
     */
    private long beginTimestamp = 0;

    /**
     * 重做次数
     */
    private int retryCount = -1;

    /**
     * 处理
     */
    public void handle() {
        retryCount++;
        beginTimestamp = System.currentTimeMillis();
    }

}
