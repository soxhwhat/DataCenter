package com.juphoon.rtc.datacenter;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>启动类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@SpringBootApplication
@EnableScheduling
@CubeStarterApplication
public class E01DataCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(E01DataCenterApplication.class, args);
    }
}
