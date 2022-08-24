package com.juphoon.rtc.datacenter.servicecore.controller;

import Common.CallParams;
import Common.ObjectAgent;
import Common.StrStrMap;
import DataCollection.Event;
import DataCollection.EventCollectionServiceAgent;
import Event.NoticeEventAgent;
import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.iron.cube.starter.CubeApplication;
import com.juphoon.iron.cube.starter.CubeUtils;
import com.juphoon.iron.event.CubeEventPublisher;
import com.juphoon.rtc.def.event.entity.alarm.ServerExceptionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ke.wang@juphoon.com
 * @date 2022/3/25 17:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@RestController
@Slf4j
@RequestMapping
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
@SuppressWarnings("PMD")
public class ServiceCoreTestController {

    @Autowired
    CubeApplication cubeApplication;

    @GetMapping("testLogin")
    public Object testLogin(int type) {
        ObjectAgent agent = cubeApplication.getAgent("#NoticeEvent");
        StrStrMap.Holder outParams = new StrStrMap.Holder();
        CallParams callParams = CubeUtils.newCallParams();
        callParams.setParam("account", "[username:test@100645.cloud.justalk.com]");
        boolean ret = NoticeEventAgent.keepAlive(agent, type, "juphoon", new HashMap<>(0), outParams, callParams);
        if (!ret) {
            return ObjectAgent.getLastReason();
        }
        return outParams.value;
    }

    @GetMapping("testDataCollection")
    public Object testDataCollection(Integer number) {
        ObjectAgent agent = cubeApplication.getAgent("#EventCollectionServer");
        StrStrMap.Holder outParams = new StrStrMap.Holder();
        CallParams callParams = CubeUtils.newCallParams();
        callParams.setParam("account", "[username:test@100645.cloud.justalk.com]");
        Event event = new Event();
        event.domainId = 100645;
        event.type = 1000;
        event.eventNumber = number;
        event.timestamp = System.currentTimeMillis();
        Map<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("key", "value");
        String s = IronJsonUtils.objectToJson(objectObjectHashMap);
        event.params = s;

        List<Event> events = Arrays.asList(event);
        boolean ret = EventCollectionServiceAgent.event(agent, events, CubeUtils.newCallParams());
        if (!ret) {
            return ObjectAgent.getLastReason();
        }
        return outParams.value;
    }

    @Autowired
    private CubeEventPublisher cubeEventPublisher;

    @GetMapping("/event/exception")
    public String serverExceptionEvent(@RequestParam("msg") String msg) throws Exception {
        log.info("get msg:{}", msg);

        Map<String, String> tags = new HashMap<>(1);

        ServerExceptionEvent.Content content = ServerExceptionEvent.Content.builder()
                .eventCode(0).domainCode("1").host("2").ignoreCount(3).level(4).location("5").message("6").state(7)
                .tags(tags).build();

        ServerExceptionEvent cubeEvent = new ServerExceptionEvent();
        cubeEvent.setParams(content);

        cubeEventPublisher.publishEvent(cubeEvent);

        return msg;
    }
}
