package com.juphoon.rtc.datacenter.cube.service;

import Common.ServerCall;
import DataCollection.FlowStatusJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.accepter.IEventRouter;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.UnsupportedDataTypeException;
import java.util.*;

import static com.juphoon.rtc.datacenter.cube.service.AbstractServerProcess.binaryToHexString;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/4/8 10:00
 * @Description:
 */
@Slf4j
@Component
public class StatusCollectionServerProcess {

    /// todo 重要
//    @Autowired
//    private IEventRouter eventRouter;

    public boolean process(ServerCall serverCall, Object status) {
        boolean ret = true;
        try {
            EventContext ec = null;
            if (status instanceof FlowStatusJson) {
                ec = trans((FlowStatusJson) status, serverCall.getParam("host"), binaryToHexString(serverCall.getMagic()));
            } else if (status instanceof List) {
                ec = trans((List<FlowStatusJson>) status, serverCall.getParam("host"), binaryToHexString(serverCall.getMagic()));
            } else {
                throw new UnsupportedDataTypeException("status 类型错误");
            }
//            eventRouter.router(Arrays.asList(ec));
        } catch (Exception e) {
            ret = false;
            serverCall.setReason(e.getMessage());
        }
        return ret;
    }

    /**
     * 转换
     *
     * @param from
     * @param host
     * @param magic
     * @return
     * @throws JsonProcessingException
     */
    private EventContext trans(FlowStatusJson from, String host, String magic) throws
            JsonProcessingException {
        Event t = trans(from);
        EventContext ec = new EventContext();
        ec.setRequestId(UUID.randomUUID().toString());
        ec.setEvent(t);
        ec.setFrom(host);
//        ec.setMagic(magic);
        return ec;
    }

    /**
     * 转换
     *
     * @param from
     * @param host
     * @param magic
     * @return
     * @throws JsonProcessingException
     */
    private EventContext trans(List<FlowStatusJson> from, String host, String magic) throws
            JsonProcessingException {
        EventContext ec = trans(from.get(0), host, magic);
        List<Event> events = new ArrayList<>();
        for (FlowStatusJson f : from) {
            events.add(trans(f));
        }
//        ec.setEventList(events);
        return ec;
    }

    /**
     * 类型转换
     * TODO 严格的参数类型检查
     *
     * @param from
     * @return
     * @throws JsonProcessingException
     */
    private Event trans(FlowStatusJson from) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };

        Map<String, Object> params = mapper.readValue(from.params, typeRef);

        return Event.builder()
                .domainId((int) from.getDomainId())
                .appId((int) from.getAppId())
                .type(from.getType())
                .number(from.getStatus())
                .params(params)
                .uuid(from.getUniqueId())
                .build();
    }

}
