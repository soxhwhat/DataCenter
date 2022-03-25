package com.juphoon.rtc.datacenter;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.service.DataService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 10:32
 * @Description:
 */
@SpringBootTest(classes = DataCenterApplication.class)
@ActiveProfiles("mysql")
public class Test {

    @Autowired
    private DataService dataService;

    @SneakyThrows
    @org.junit.jupiter.api.Test
    public void t1() {
        Event event = new Event();
        event.setType(0);
        event.setNumber(0);
        Map<String, String> map = new HashMap<>();
        map.put("beginTimeStamp", "2021-05-06 10:20:30");
        map.put("beginTimeStamp", "2021-05-06 10:30:30");
        event.setParams(map);
        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        dataService.commit(eventContext);
        Thread.sleep(1000L);
    }

    @SneakyThrows
    @org.junit.jupiter.api.Test
    public void databaseTest() {
        Event event = new Event();
        event.setType(10);
        event.setNumber(1);
        Map<String, String> map = new HashMap<>(8);
        map.put("appId", "0");
        map.put("domainId", "100645");
        map.put("duration", "1000");
        map.put("endType", "0");
        map.put("eventNum", "1");
        map.put("eventType", "10");
        map.put("skill", "111");
        map.put("beginTimestamp", "1647935486000");
        map.put("endTimestamp", "1647942686000");
        event.setParams(map);
        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        dataService.commit(eventContext);
        Thread.sleep(5000L);
    }
}
