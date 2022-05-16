package com.juphoon.rtc.datacenter.cube.service;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import DataCollection.EventCollectionServiceServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.CubeUtils;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>EventCollectionServer RPC服务实现</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2022-03-25
 */
@Slf4j
@CubeService(serviceName = "#EventCollectionServer")
@SuppressWarnings("PMD")
public class EventCollectionServiceServerImpl extends AbstractCubeService {

    @Autowired
    private EventService eventService;

    @Override
    public ObjectServer buildServiceServer() {
        return new EventCollectionServiceServer() {
            @Override
            public void event_begin(ServerCall serverCall, List<DataCollection.Event> eventList) throws Exception {
                boolean ret = true;
                try {
                    eventService.commit(process(serverCall, eventList));
                } catch (java.lang.Exception e) {
                    log.warn("process fail", e);
                    ret = false;
                } finally {
                    event_end(serverCall, ret);
                }
            }

            @Override
            public void event2_begin(ServerCall serverCall, String topic, List<DataCollection.Event> eventList) throws Exception {
                boolean ret = true;
                try {
                    eventService.commit(process(serverCall, eventList));
                } catch (java.lang.Exception e) {
                    log.warn("process fail", e);
                    ret = false;
                } finally {
                    event2_end(serverCall, ret);
                }
            }

            @Override
            public boolean subEvent(ServerCall serverCall) throws Exception {
                return false;
            }
        };
    }

    private List<EventContext> process(ServerCall serverCall, List<DataCollection.Event> events) throws JsonProcessingException {
        String magic = CubeUtils.bytesToHex(serverCall.getMagic());

        log.debug("magic:{}", magic);

        String host = serverCall.getParam("host");

        List<EventContext> ret = new ArrayList<>();
        for (DataCollection.Event event : events) {
            EventContext ec = new EventContext();

            ec.setRequestId(magic);
            ec.setFrom(host);
            ec.setEvent(trans(event));

            ret.add(ec);
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