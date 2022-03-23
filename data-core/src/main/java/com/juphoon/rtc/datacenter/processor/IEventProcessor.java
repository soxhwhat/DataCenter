package com.juphoon.rtc.datacenter.processor;


import com.juphoon.rtc.datacenter.api.EventContext;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 9:44
 * @Description:
 */
public interface IEventProcessor {


    /**
     * 处理事件
     *
     * @param ec
     * @throws Exception
     */
    void process(EventContext ec);
}
