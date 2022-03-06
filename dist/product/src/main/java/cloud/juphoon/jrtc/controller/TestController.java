package cloud.juphoon.jrtc.controller;

import cloud.juphoon.jrtc.api.Event;
import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 10:55
 * @Description:
 */
@RestController
public class TestController {

    @Autowired
    DataService dataService;

    @RequestMapping("/t1")
    public void t1(){
        Event event = new Event();
        event.setType(0);
        event.setNumber(0);
        Map<String ,String> map = new HashMap();
        map.put("beginTimeStamp","2021-05-06 10:20:30");
        map.put("beginTimeStamp","2021-05-06 10:30:30");
        event.setParams(map);
        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        dataService.commit(eventContext);
    }
}
