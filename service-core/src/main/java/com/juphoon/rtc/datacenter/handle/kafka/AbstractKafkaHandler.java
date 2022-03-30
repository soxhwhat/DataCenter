package com.juphoon.rtc.datacenter.handle.kafka;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;


/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:51
 * @Description:
 */
public abstract class AbstractKafkaHandler extends AbstractEventHandler {

    public String getTopic(EventContext ec) {
        Integer type = ec.getEvent().getEventType().getType();
        if (type.equals(EventType.TICKER_EVENT_WAIT.getType())) {
            return "ticket_events";
        } else if (type.equals(EventType.STAFF_BEAT.getType())) {
            return "agent_status";
        } else if (type.equals(EventType.QUEUE_BEAT.getType())) {
            return "queue.status";
        }
        return null;
    }

}
