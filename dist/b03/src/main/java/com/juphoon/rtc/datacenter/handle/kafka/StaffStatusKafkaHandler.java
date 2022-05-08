package com.juphoon.rtc.datacenter.handle.kafka;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.entity.FlowTicket;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.STAFF_BEAT;

/**
 * 坐席状态上报处理器
 *
 * @author zhiwei.zhai
 * @date 2022-04-27
 */
@Slf4j
@Component
public class StaffStatusKafkaHandler extends AbstractKafkaHandler {

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(STAFF_BEAT);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.StaffStatusKafkaHandler;
    }

    @Override
    String getTopic(EventContext ec) {
        return kafkaProperties.getTopic().getStaff();
    }

    @Override
    Object getData(EventContext ec) {
        Event event = ec.getEvent();
        FlowTicket flowTicket = new FlowTicket();
        flowTicket.setStatus(event.getNumber());
        flowTicket.setAppId(String.valueOf(event.appId()));
        flowTicket.setDomainId(String.valueOf(event.domainId()));
        flowTicket.setParams(new Document(event.getParams()));
        flowTicket.setType(event.eventType());
        flowTicket.setUniqueId(event.getUuid());
        flowTicket.setUpdateTime(System.currentTimeMillis());
        return flowTicket;
    }
}
