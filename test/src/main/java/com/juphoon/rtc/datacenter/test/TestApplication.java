package com.juphoon.rtc.datacenter.test;

import lombok.extern.slf4j.Slf4j;
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
@SpringBootApplication
@RestController
@Slf4j
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }


    @GetMapping("/test")
    public String test(@RequestParam(value = "a", required = false) String a,
                       @RequestParam(value = "b", required = false) String b,
                       @RequestParam(value = "c", required = false) String c,
                       @RequestParam(value = "name", required = false) String name) {
        log.info("a:{}", a);
        log.info("b:{}", b);
        log.info("c:{}", c);
        log.info("name:{}", name);

        return "good";
    }
}
