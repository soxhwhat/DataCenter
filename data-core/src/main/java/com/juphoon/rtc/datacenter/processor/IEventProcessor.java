package com.juphoon.rtc.datacenter.processor;


import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.INamed;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 9:44
 * @update:
 * <p>1. 2022-03-24. ajian.zheng 增加可命名接口</p>
 */
public interface IEventProcessor extends INamed {
    /**
     * 处理事件
     *
     * @param ec
     * @throws Exception
     */
    void process(EventContext ec);
}
