package com.juphoon.rtc.datacenter.cube.service;

import Common.ServerCall;
import DataCollection.FlowStatusJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.accepter.IEventRouter;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/4/8 10:00
 * @Description:
 */
@Slf4j
@Component
public class StatusCollectionServerProcess {

    @Autowired
    private IEventRouter eventRouter;

    public boolean process(ServerCall serverCall, List<FlowStatusJson> status) {
        boolean ret = true;
        try {

            EventContext ec = trans(status, serverCall.getParam("host"), binaryToHexString(serverCall.getMagic()));
            eventRouter.router(Arrays.asList(ec));
        } catch (Exception e) {
            ret = false;
            serverCall.setReason(e.getMessage());
        }
        return ret;
    }

    public boolean process(ServerCall serverCall, FlowStatusJson status) {
        boolean ret = true;
        try {
            EventContext ec = trans(status, serverCall.getParam("host"), binaryToHexString(serverCall.getMagic()));
            eventRouter.router(Arrays.asList(ec));
        } catch (Exception e) {
            ret = false;
            serverCall.setReason(e.getMessage());
        }
        return ret;
    }


    /**
     * 转换
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
        ec.setMagic(magic);
        return ec;
    }

    /**
     * 转换
     * @param from
     * @param host
     * @param magic
     * @return
     * @throws JsonProcessingException
     */
    private EventContext trans(List<FlowStatusJson> from, String host, String magic) throws
            JsonProcessingException {
        EventContext ec = trans(from.get(0),host,magic);
        List<Event> events = new ArrayList<>();
        for (FlowStatusJson f : from){
            events.add(trans(f));
        }
        ec.setEventList(events);
        return ec;
    }

    /**
     * 类型转换
     * TODO 严格的参数类型检查
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

    private static final String HEX_STR = "0123456789ABCDEF";

    private static String binaryToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        String hex;
        for (byte aByte : bytes) {
            //字节高4位
            hex = String.valueOf(HEX_STR.charAt((aByte & 0xF0) >> 4));
            //字节低4位
            hex += String.valueOf(HEX_STR.charAt(aByte & 0x0F));
            result.append(hex);
        }
        return result.toString();
    }

}
