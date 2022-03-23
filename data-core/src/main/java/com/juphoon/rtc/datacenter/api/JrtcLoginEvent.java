package com.juphoon.rtc.datacenter.api;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/21/22 3:56 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class JrtcLoginEvent {
    private Event event;

    public long getDuration() {
        return (Long) event.getParams().get("duration");
    }
}
