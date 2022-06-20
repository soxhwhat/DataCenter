package com.juphoon.rtc.datacenter;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author  ke.wang@juphoon.com
 * @date   2022/4/27
 * @update 2022/04/28 Yuan wenjun
 */
@SpringBootApplication
@EnableScheduling
@CubeStarterApplication(basePackages = "com.juphoon")
public class B03DataCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(B03DataCenterApplication.class, args);
    }
}
