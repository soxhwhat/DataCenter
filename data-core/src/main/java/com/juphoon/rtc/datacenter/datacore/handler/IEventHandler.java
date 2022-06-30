package com.juphoon.rtc.datacenter.datacore.handler;

import com.juphoon.rtc.datacenter.datacore.api.*;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.ICare;
import com.juphoon.rtc.datacenter.datacore.api.INamed;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
public interface IEventHandler extends ICare, INamed {
    /**
     * 执行入口
     *
     * @param ec
     * @return
     * @throws Exception
     */
    boolean handle(EventContext ec) throws Exception;

    /**
     * 必须指定一个handlerId
     *
     * @return
     */
    HandlerId handlerId();

    /**
     * 获取handler 名
     * @return
     */
    @Override
    default String getName() {
        return handlerId().getName();
    }

    /**
     * 获取handler Id
     * @return
     */
    @Override
    default String getId() {
        return handlerId().getId();
    }
}
