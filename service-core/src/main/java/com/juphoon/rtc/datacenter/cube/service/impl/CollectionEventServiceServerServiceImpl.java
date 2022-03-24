package com.juphoon.rtc.datacenter.cube.service.impl;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import com.alibaba.fastjson.JSON;
import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.cube.model.Event;
import com.juphoon.rtc.datacenter.cube.model.EventMapPO;
import com.juphoon.rtc.datacenter.cube.service.EventCollectionServiceServer;
import com.juphoon.rtc.datacenter.service.DataService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;


@Setter
@Slf4j
@Component
@CubeService(serviceName = "#EventCollectionServer")
public class CollectionEventServiceServerServiceImpl extends AbstractCubeService {// extends EventCollectionServiceServer {


    @Autowired
    private DataService dataService;


    @Override
    public ObjectServer buildServiceServer() {
        return new EventCollectionServiceServer() {
            @Override
            public void event_begin(ServerCall __call, List<Event> eventList) throws Exception {
                try {
                    if (CollectionUtils.isEmpty(eventList)) {
                        event_end(__call, true);
                    } else {
                        String host = __call.getParam("host");
                        for (Event event : eventList) {
                            EventContext eventContext = buildEventContext(event);
                            eventContext.getEvent().getParams().put("host", host);
                            dataService.commit(eventContext);
                        }
                        event_end(__call, true);
                    }
                } catch (java.lang.Exception e) {
                    __call.setReason(e.getMessage());
                    event_end(__call, false);
                }
            }

            @Override
            public void event2_begin(ServerCall __call, String topic, List<Event> eventList) throws Exception {
                try {
                    //TODO 测试后去掉日志
                    log.info("event2_begin.eventList:{}", IronJsonUtils.objectToJson(eventList));
                    if (!CollectionUtils.isEmpty(eventList)) {
                        for (Event event : eventList) {
                            EventContext eventContext = buildEventContext(event);
                            dataService.commit(eventContext);
                        }
                    }
                    event2_end(__call, true);
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                    __call.setReason(e.getMessage());
                    event2_end(__call, false);
                }

            }
        };
    }

    private EventContext buildEventContext(Event event) {
        EventContext eventContext = new EventContext();
        //封装EventMapPO
        EventMapPO eventMapPO = new EventMapPO();
        eventMapPO.setEventNumber(event.getEventNumber());
        eventMapPO.setAppId(event.getAppId());
        eventMapPO.setDomainId(event.getDomainId());
        eventMapPO.setTags(event.getTags());
        eventMapPO.setTimestamp(event.getTimestamp());
        eventMapPO.setUuid(event.getUuid());
        eventMapPO.setType(event.getType());
        eventMapPO.setParams(JSON.parseObject(event.getParams(),Map.class));
        //封装dataEvent
        com.juphoon.rtc.datacenter.api.Event dataEvent = new com.juphoon.rtc.datacenter.api.Event();
        dataEvent.setNumber(event.getEventNumber());
        dataEvent.setType(event.getType());
        dataEvent.setParams(JSON.parseObject(JSON.toJSONString(eventMapPO), Map.class));
        //返回
        eventContext.setEvent(dataEvent);
        return eventContext;
    }
}

