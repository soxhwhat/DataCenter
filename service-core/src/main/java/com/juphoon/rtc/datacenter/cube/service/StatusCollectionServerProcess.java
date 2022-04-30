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

    public boolean process(ServerCall serverCall, List<FlowStatusJson> statusList) {
        String magic = binaryToHexString(serverCall.getMagic());
        log.debug("magic:{}", magic);

        boolean ret = true;
        try {
            if (null == statusList || statusList.isEmpty()) {
                log.warn("empty");
                throw new IllegalArgumentException("eventListIsEmpty");
            }
            String host = serverCall.getParam("host");
            List<EventContext> eventContexts = trans(statusList, host, magic);
            eventRouter.router(eventContexts);
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
    private List<EventContext> trans(List<FlowStatusJson> from, String host, String magic) throws
            JsonProcessingException {
        List<EventContext> ret = new ArrayList<>();

        for (FlowStatusJson f : from) {
            Event t = trans(f);
            EventContext ec = new EventContext();
            ec.setRequestId(UUID.randomUUID().toString());
            ec.setEvent(t);
            ec.setFrom(host);
            ec.setMagic(magic);
            ret.add(ec);
        }

        return ret;
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
                .number(0)
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
