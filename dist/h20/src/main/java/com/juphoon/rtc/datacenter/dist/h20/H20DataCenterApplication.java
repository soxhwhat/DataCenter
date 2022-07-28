package com.juphoon.rtc.datacenter.dist.h20;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wenjun.yuan@juphoon.com
 * @date 2022/7/22
 */
@EnableScheduling
@CubeStarterApplication(basePackages = "com.juphoon")
@MapperScan(basePackages = "com.juphoon.rtc.datacenter")
@SpringBootApplication(scanBasePackages = "com.juphoon.rtc.datacenter")
public class H20DataCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(H20DataCenterApplication.class, args);
    }

}
