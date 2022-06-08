package com.juphoon.rtc.datacenter.api;

/**
 * <p>服务抽象接口</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/17/22 5:26 PM
 */
public interface IService {

    /**
     * 启动方法
     */
    default void start() {
    }

    /**
     * 停止方法
     */
    default void stop() {
    }
}
