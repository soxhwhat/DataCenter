package com.juphoon.rtc.datacenter.cube;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import DataCollection.EventCollectionServiceServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import com.juphoon.rtc.datacenter.accepter.IEventRouter;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
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
    private IEventRouter eventRouter;

    @Override
    public ObjectServer buildServiceServer() {
        return new EventCollectionServiceServer() {
            @Override
            public void event_begin(ServerCall serverCall, List<DataCollection.Event> eventList) throws Exception {
                event_end(serverCall, process(serverCall, eventList));
            }

            @Override
            public void event2_begin(ServerCall serverCall, String topic, List<DataCollection.Event> eventList) throws Exception {
                event_end(serverCall, process(serverCall, eventList));
            }

            @Override
            public boolean subEvent(ServerCall serverCall) throws Exception {
                return false;
            }
        };
    }

    private boolean process(ServerCall serverCall, List<DataCollection.Event> eventList) {
        boolean ret = true;
        try {
            if (null == eventList || eventList.isEmpty()) {
                log.warn("empty");
                throw new IllegalArgumentException("eventListIsEmpty");
            }

            String host = serverCall.getParam("host");
            List<EventContext> eventContexts = trans(eventList, host);
            eventRouter.router(eventContexts);
        } catch (java.lang.Exception e) {
            ret = false;
            serverCall.setReason(e.getMessage());
        }

        return ret;
    }

    private List<EventContext> trans(List<DataCollection.Event> from, String host) throws
            JsonProcessingException {
        List<EventContext> ret = new ArrayList<>();

        for (DataCollection.Event f : from) {
            Event t = trans(f);

            EventContext ec = new EventContext();

            ec.setRequestId(t.getUuid());
            ec.setEvent(t);
            ec.setFrom(host);

            ret.add(ec);
        }

        return ret;
    }

    /// TODO 严格的参数类型检查
    private Event trans(DataCollection.Event from) throws JsonProcessingException {
        Event to = new Event();

        to.setDomainId((int) from.domainId);
        to.setAppId((int) from.appId);
        to.setUuid(from.uuid);
        to.setType(from.type);
        to.setNumber(from.eventNumber);

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
        };

        Map<String, String> params = mapper.readValue(from.params, typeRef);

        to.setParams(params);

        return to;
    }
}