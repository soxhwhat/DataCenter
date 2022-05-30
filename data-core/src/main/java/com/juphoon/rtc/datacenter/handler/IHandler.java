package com.juphoon.rtc.datacenter.handler;

import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.ICare;
import com.juphoon.rtc.datacenter.api.INamed;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
public interface IHandler<T> extends ICare, INamed {
    /**
     * 执行入口
     *
     * @param t
     * @return
     * @throws Exception
     */
    boolean handle(T t) throws Exception;

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
