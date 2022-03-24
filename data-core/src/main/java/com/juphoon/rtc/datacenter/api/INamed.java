package com.juphoon.rtc.datacenter.api;

/**
 * <p>可命名对象</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    3/24/22 2:30 PM
 */
public interface INamed {
    /**
     * 获取名字
     *
     * @return
     */
    String getName();

    /**
     * 获取ID
     *
     * @return
     */
    String getId();
}
