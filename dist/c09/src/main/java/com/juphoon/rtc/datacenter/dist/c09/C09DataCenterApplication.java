package com.juphoon.rtc.datacenter.dist.c09;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Jiahui.huang
 * @date 2022-08-02
 */
@EnableScheduling
@CubeStarterApplication(basePackages = "com.juphoon")
@MapperScan(basePackages = "com.juphoon.rtc.datacenter")
@SpringBootApplication(scanBasePackages = "com.juphoon.rtc.datacenter")
public class C09DataCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(C09DataCenterApplication.class, args);
    }

}
