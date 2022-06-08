package com.juphoon.rtc.datacenter.test;

import com.juphoon.rtc.datacenter.api.*;
import com.juphoon.rtc.datacenter.service.EventService;
import com.juphoon.rtc.datacenter.service.LogService;
import com.juphoon.rtc.datacenter.service.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.*;

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
    private LogService logService;

    @Autowired
    private StateService stateService;

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

    @GetMapping("/log")
    public String logTest(@RequestParam("msg") String msg) throws InvalidParameterException {
        log.info("get msg:{}", msg);

        List<String> logs = new ArrayList<>(1);
        logs.add(msg);

        LogContext context = new LogContext(logs);
        context.setEventType(EventType.TEST_EVENT);
        context.setRequestId(msg);
        context.setFrom(msg);

        logService.commit(context);

        return "log";
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
