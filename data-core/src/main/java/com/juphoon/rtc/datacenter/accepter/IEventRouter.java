package com.juphoon.rtc.datacenter.accepter;

import com.juphoon.rtc.datacenter.api.EventContext;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/8 17:41
 * @Description:
 */
public interface IEventRouter {

    /**
     * 消息路由
     *
     * @param ec
     */
    void router(EventContext ec);
}
