package com.juphoon.rtc.datacenter.handler;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventType;

import java.util.List;

/**
 * <p>HTTP事件处理handler抽象</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 3:18 PM
 */
public abstract class AbstractCareAllEventHandler extends AbstractEventHandler {

    @Override
    public List<EventType> careEvents() {
        return null;
    }

    @Override
    public boolean care(Event event) {
        return true;
    }

}
