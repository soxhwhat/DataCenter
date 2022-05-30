package com.juphoon.rtc.datacenter;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>示例工程</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 */
@Slf4j
@SpringBootApplication
@RestController
public class DemoDataCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoDataCenterApplication.class, args);
    }

    @Autowired
    private EventService eventService;

    @GetMapping("/event")
    public void event(@RequestParam(value = "value", required = false) int value) {
        log.info("value:{}", value);
        EventContext ec = new EventContext();

        Map<String, Object> params = new HashMap<>(9);
        params.put("callId", "123456789");
        params.put("duration", "1");
        params.put("beginTimestamp", "1");
        params.put("endType", "0");
        params.put("appId", "4");
        params.put("endTimestamp", "2");
        params.put("guestId", "lisi");
        params.put("domainId", "100645");
        params.put("queue", "10087");

        Event event = Event.builder().type(10).number(0).params(params).build();

        ec.setEvent(event);

        eventService.commit(ec);
    }

    @GetMapping("/record")
    public void record(@RequestParam(value = "value", required = false) int value) {
        log.info("value:{}", value);
        EventContext ec = new EventContext();

        Map<String, Object> params = new HashMap<>(13);

        params.put("callId", "987654321");
        params.put("agentId", "zhangsan");
        params.put("talkTime", "1");
        params.put("beginTimestamp", "1");
        params.put("endType", "110102");
        params.put("domainId", "100645");
        params.put("ringingTime", "1");
        params.put("appId", "4");
        params.put("endTimestamp", "2");
        params.put("guestId", "lisi");
        params.put("waitTime", "1");
        params.put("queue", "10086");
        params.put("recordMode", "true");

        Event event = Event.builder().type(11).number(1).params(params).build();

        ec.setEvent(event);

        eventService.commit(ec);
    }
}
