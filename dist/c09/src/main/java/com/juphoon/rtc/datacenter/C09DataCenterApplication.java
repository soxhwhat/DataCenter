package com.juphoon.rtc.datacenter;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>启动类</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @date 5/20/22 16:01 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@SpringBootApplication
@EnableScheduling
@CubeStarterApplication
public class C09DataCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(C09DataCenterApplication.class, args);
    }
}
