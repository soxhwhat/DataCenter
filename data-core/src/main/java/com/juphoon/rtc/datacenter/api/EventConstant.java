package com.juphoon.rtc.datacenter.api;

/**
 * <p>事件常量列表</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/17/22 8:25 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class EventConstant {
    // 排队机事件

    public static EventType EVENT_ACD_CALL = new EventType(1, 1);


    // XXX事件

    public static EventType EVENT_ROOM_JOIN = new EventType(1, 2);
}
