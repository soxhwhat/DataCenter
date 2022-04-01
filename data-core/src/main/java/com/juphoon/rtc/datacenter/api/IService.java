package com.juphoon.rtc.datacenter.api;

/**
 * <p>服务抽象接口</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    2/17/22 5:26 PM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */ 
public interface IService {

    /**
     * 启动方法
     */
    void start();

    /**
     * 停止方法
     */
    void stop();
}
