package cloud.juphoon.jrtc.controller;

import ch.qos.logback.core.util.TimeUtil;
import cloud.juphoon.jrtc.api.Event;
import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.processor.NoticeHttpProcessor;
import cloud.juphoon.jrtc.processor.TestHttpProcessor;
import cloud.juphoon.jrtc.service.DataService;
import cloud.juphoon.jrtc.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 10:55
 * @Description:
 */
@RestController
@Slf4j
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
public class TestController {


    @Autowired
    DataService dataService;

    @Autowired
    NoticeHttpProcessor noticeHttpProcessor;

    @Autowired(required = false)
    TestHttpProcessor testHttpProcessor;

    @GetMapping("testHttpClient")
    public Object testHttpClient1(Integer num) {
        HttpClientUtil template = noticeHttpProcessor.getTemplate(noticeHttpProcessor.getConfig());
        HttpClientUtil template2 = testHttpProcessor.getTemplate(testHttpProcessor.getConfig());
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", "test");

        for (int i = 0; i < num; i++) {
            int finalI = i;
            new Thread(() -> {
                String post = null;
                String post1 = null;
                if (finalI % 2 == 0) {
                    post = template.post("http://127.0.0.1:8089/testHttpClient?name=template1" + finalI, paramMap, null);
                } else {
                    post1 = template2.post("http://127.0.0.1:8089/testHttpClient?name=template2" + finalI, paramMap, null);
                }
                log.info("response:{}", post);
                log.info("response1:{}", post1);
            }).start();
        }
        return "ok";
    }


    @GetMapping
    public Object testNoticeHandler() {
        Event event = new Event();
        event.setType(25);
        event.setNumber(1);
        Map<String, Object> map = new HashMap();
        event.setParams(map);
        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        dataService.commit(eventContext);
        return "ok";
    }

    @RequestMapping("/t1")
    public void t1() {
        Event event = new Event();
        event.setType(0);
        event.setNumber(0);
        Map<String, Object> map = new HashMap();
        map.put("beginTimeStamp", "2021-05-06 10:20:30");
        map.put("beginTimeStamp", "2021-05-06 10:30:30");
        event.setParams(map);
        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        dataService.commit(eventContext);
    }

    @RequestMapping("/t2")
    public void t2() {
        Event event = new Event();
        event.setType(10);
        event.setNumber(1);
        Map<String, Object> map = new HashMap(8);
        map.put("appId", 0);
        map.put("domainId", 100645);
        map.put("duration", (long) 1000);
        map.put("endType", (short) 0);
        map.put("eventNum", (short) 1);
        map.put("eventType", (short) 10);
        map.put("skill", "111");
        map.put("statType", (byte) 1);
        map.put("statTime", LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        event.setParams(map);
        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        dataService.commit(eventContext);
    }

    /**
     * 测试两个httpClient连接池互不影响的接口
     */
    @PostMapping("testHttpClient")
    public Map testHttpClient(String name) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        log.info("回调======》 str:{}", name);
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", name);
        return objectObjectHashMap;
    }


    /**
     * ver_code 用户登录回调测试接口
     */
    @PostMapping("userlogin_notify")
    public Map noticeLogin(String name) {
        log.info("回调======》 str:{}", name);
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", name);
        return objectObjectHashMap;
    }

    /**
     * ver_code 事件回调测试接口
     */
    @PostMapping("userlogin_request")
    public Map userlogin_request(String name) throws InterruptedException {
        log.info("回调======》 str:{}", name);
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", name);
        return objectObjectHashMap;
    }
}
