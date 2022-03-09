package cloud.juphoon.jrtc;

import cloud.juphoon.jrtc.api.Event;
import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.service.DataService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 10:32
 * @Description:
 */
@SpringBootTest(classes = DataCenterApplication.class)
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
}
