package com.juphoon.rtc.datacenter.cube.service;

import Common.Exception;
import Common.ServerCall;
import DataCollection.EventCollectionServiceServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.iron.cube.starter.CubeUtils;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/4/8 10:00
 * @Description:
 */
@Slf4j
@Component
@SuppressWarnings("PMD")
public class EventCollectionServer extends EventCollectionServiceServer {

    @Autowired
    private EventService eventService;

    @Override
    public void event_begin(ServerCall serverCall, List<DataCollection.Event> eventList) throws Exception {
        event_end(serverCall, process(serverCall, eventList));
    }

    @Override
    public void event2_begin(ServerCall serverCall, String topic, List<DataCollection.Event> eventList) throws Exception {
        event2_end(serverCall, process(serverCall, eventList));
    }

    @Override
    public boolean subEvent(ServerCall serverCall) throws Exception {
        log.error("不该调用 from {}", serverCall.getParam("host"));
        return false;
    }

    private boolean process(ServerCall serverCall, List<DataCollection.Event> events) throws Common.Exception {
        boolean ret = true;
        try {
            String magic = CubeUtils.bytesToHex(serverCall.getMagic());

            log.debug("magic:{}", magic);

            String host = serverCall.getParam("host");

            List<EventContext> contexts = new LinkedList<>();
            for (DataCollection.Event event : events) {
                EventContext ec = new EventContext(trans(event));

                ec.setRequestId(magic);
                ec.setFrom(host);

                contexts.add(ec);

                log.debug("ec:{}", ec.dump());
            }

            eventService.commit(contexts);
        } catch (JsonProcessingException e) {
            log.warn("process fail", e);
            ret = false;
        } catch (java.lang.Exception e) {
            log.warn("process fail", e);
            ret = false;
        }

        return ret;
    }

    private Event trans(DataCollection.Event from) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        Map<String, Object> params = mapper.readValue(from.params, typeRef);
        return Event.builder()
                .domainId((int) from.domainId)
                .appId((int) from.appId)
                .type(from.type)
                .number(from.eventNumber)
                .timestamp(from.timestamp)
                .params(params)
                .uuid(from.uuid)
                .build();
    }

}
