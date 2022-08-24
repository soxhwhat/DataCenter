package com.juphoon.rtc.datacenter.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.datacore.api.*;
import com.juphoon.rtc.datacenter.datacore.service.EventService;
import com.juphoon.rtc.datacenter.datacore.service.StateService;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity.MongoEventPO;
import com.juphoon.rtc.def.event.entity.alarm.ServerExceptionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.*;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/17/22 9:36 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    private EventService eventService;

    @Autowired
    private StateService stateService;

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @GetMapping("/event")
    public String event(@RequestParam("msg") String msg) throws InvalidParameterException {
        log.info("get msg:{}", msg);

        Map<String, Object> params = new HashMap<>(1);
        params.put("k", "v");

        Event event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(-1)
                .number(-1)
                .timestamp(System.currentTimeMillis())
                .params(params)
                .uuid(UUID.randomUUID().toString())
                .build();

        EventContext ec = new EventContext(event);
        ec.setRequestId(event.getUuid());
        ec.setFrom(msg);

        List<EventContext> data = new ArrayList<>();
        data.add(ec);

        eventService.commit(data);

        return "event";
    }

    @GetMapping("/event/serverException")
    public String serverExceptionEvent(@RequestParam("msg") String msg) throws Exception {
        log.info("get msg:{}", msg);

        Map<String, String> tags = new HashMap<>(1);
        tags.put("k", "v");

        ServerExceptionEvent cubeEvent = new ServerExceptionEvent();

        ServerExceptionEvent.Content content = ServerExceptionEvent.Content.builder()
                .eventCode(0).domainCode("1").host("2").ignoreCount(3).level(4).location("5").message("6").state(7)
                .tags(tags).build();

        cubeEvent.setParams(content);

        ObjectMapper objectMapper = new ObjectMapper();
        String params = objectMapper.writeValueAsString(content);

        Event event = Event.builder()
                .domainId(cubeEvent.getDomainId())
                .appId(cubeEvent.getAppId())
                .type(cubeEvent.getEventType().getType())
                .number(cubeEvent.getEventType().getNumber())
                .timestamp(cubeEvent.getTimestamp())
                .uuid(cubeEvent.getUuid())
                .params(null)
                .stringParams(params)
                .build();

        EventContext ec = new EventContext(event);
        ec.setRequestId(event.getUuid());
        ec.setFrom(msg);

        List<EventContext> data = new ArrayList<>();
        data.add(ec);

        eventService.commit(data);

        return event.getUuid();
    }

    @GetMapping("/state")
    public String state(@RequestParam("msg") String msg) throws InvalidParameterException {
        log.info("get msg:{}", msg);

        StateContext context = randomContext(EventType.TEST_EVENT, msg);
        context.setRequestId(msg);
        context.setFrom(msg);

        stateService.commit(context);

        return "state";
    }

    @GetMapping("/getEvent")
    public String getEvent(@RequestParam("msg") String msg) throws InvalidParameterException {
        log.info("get msg:{}", msg);

        Query query = new Query(Criteria.where("timestamp").gt(0));

        List<MongoEventPO> list = mongoTemplate.find(query, MongoEventPO.class);

        list.forEach(po -> log.info("{}", po));

        return "state";
    }

    public static StateContext randomContext(EventType eventType, String msg) {
        StateContext context = new StateContext(randomState(eventType, msg));
        String random = UUID.randomUUID().toString();

        context.setFrom(random);
        context.setRequestId(random);
        context.setProcessorId("test");

        return context;
    }

    public static State randomState(EventType eventType, String msg) {
        return State.builder().domainId(100645).appId(0).type(eventType.getType()).state(eventType.getNumber()).uuid(UUID.randomUUID().toString())
                .params(msg).build();
    }
}
