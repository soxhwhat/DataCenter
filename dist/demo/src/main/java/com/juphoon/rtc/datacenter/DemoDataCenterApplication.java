package com.juphoon.rtc.datacenter;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private DataService dataService;

    @GetMapping("/test")
    public void test(@RequestParam(value = "value", required = false) int value) {
        log.info("value:{}", value);
        EventContext ec = new EventContext();

        Event event = Event.builder().type(0).number(0).params(null).build();

        ec.setEvent(event);

        dataService.commit(ec);
    }
}
