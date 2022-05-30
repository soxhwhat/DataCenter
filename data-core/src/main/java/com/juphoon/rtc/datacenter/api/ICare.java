package com.juphoon.rtc.datacenter.api;

import java.util.List;

/**
 * <p>care抽象接口</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/17/22 5:26 PM
 */
public interface ICare {

    /**
     * 是否关心
     *
     * @param eventType
     * @return
     */
    boolean care(EventType eventType);

    /**
     * 关注事件列表
     *
     * @return
     */
    List<EventType> careEvents();
}
