package com.juphoon.rtc.datacenter.test;

import com.juphoon.rtc.datacenter.test.entity.AgreeRequestDTO;
import com.juphoon.rtc.datacenter.test.entity.TransbufferReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/userlogin_request")
    public Map<String, String> userLoginRequest(@RequestBody AgreeRequestDTO agreeRequestDTO) {
        log.info("params:{}", agreeRequestDTO);

        return buildSuccess();
    }

    @PostMapping("/prepare_enterroom")
    public Map<String, String> prepareEnterRoom(@RequestBody AgreeRequestDTO agreeRequestDTO) {
        log.info("params:{}", agreeRequestDTO);

        return buildSuccess();
    }

    @PostMapping("/room_notify")
    public Map<String, String> roomNotify(@RequestBody AgreeRequestDTO agreeRequestDTO) {
        log.info("params:{}", agreeRequestDTO);

        return buildSuccess();
    }

    @PostMapping("/recordsnapshot_notify")
    public Map recordSnapshotNotify(@RequestBody AgreeRequestDTO agreeRequestDTO) {
        log.info("params:{}", agreeRequestDTO);

        return buildSuccess();
    }

    @PostMapping("/transbuffer_notify")
    public Map recordSnapshotNotify(@RequestBody TransbufferReq agreeRequestDTO) {
        log.info("params:{}", agreeRequestDTO);

        return buildSuccess();
    }

    @PostMapping("/userlogin_notify")
    public Map userLoginNotify(@RequestBody AgreeRequestDTO agreeRequestDTO) {
        log.info("params:{}", agreeRequestDTO);

        return buildSuccess();
    }

    @PostMapping("/userlogout_notify")
    public Map userLogoutNotify(@RequestBody AgreeRequestDTO agreeRequestDTO) {
        log.info("params:{}", agreeRequestDTO);

        return buildSuccess();
    }


    public Map<String, String> buildSuccess() {
        HashMap<String, String> map = new HashMap<>();
        map.put("return_code", "SUCCESS");
        map.put("return_message", "");
        return map;
    }
}
