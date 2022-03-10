package cloud.juphoon.jrtc;

import cloud.juphoon.jrtc.api.Event;
import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.service.DataService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 10:32
 * @Description:
 */
@SpringBootTest(classes = DataCenterApplication.class)
@ActiveProfiles("oracle")
public class Test {

    @Autowired
    private DataService dataService;

    @SneakyThrows
    @org.junit.jupiter.api.Test
    public void t1(){
        Event event = new Event();
        event.setType(0);
        event.setNumber(0);
        Map<String ,Object> map = new HashMap();
        map.put("beginTimeStamp","2021-05-06 10:20:30");
        map.put("beginTimeStamp","2021-05-06 10:30:30");
        event.setParams(map);
        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        dataService.commit(eventContext);
        Thread.sleep(1000L);
    }

    @SneakyThrows
    @org.junit.jupiter.api.Test
    public void databaseTest(){
        Event event = new Event();
        event.setType(10);
        event.setNumber(1);
        Map<String ,Object> map = new HashMap(8);
        map.put("appId",0);
        map.put("domainId",100645);
        map.put("duration",(long) 1000);
        map.put("endType",(short) 0);
        map.put("eventNum",(short) 1);
        map.put("eventType",(short) 10);
        map.put("skill","111");
        map.put("statType",(byte)1);
        map.put("statTime", LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        event.setParams(map);
        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        dataService.commit(eventContext);
        Thread.sleep(5000L);
    }
}
