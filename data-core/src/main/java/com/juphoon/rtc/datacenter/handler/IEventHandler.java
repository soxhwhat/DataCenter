package com.juphoon.rtc.datacenter.handler;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.ICare;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
public interface IEventHandler extends ICare {

    /**
     * 执行入口
     *
     * @param ec
     * @return
     * @throws Exception
     */
    boolean handle(EventContext ec) throws Exception;

    /**
     * 重做
     *
     * @return
     */
    boolean isRedo();
}
