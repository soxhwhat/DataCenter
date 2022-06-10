package com.juphoon.rtc.datacenter.test;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.DOMAIN_CODE;

/**
 * <p>示例工程</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 */
@SpringBootApplication(scanBasePackages = "com.juphoon.rtc.datacenter")
@EnableScheduling
@CubeStarterApplication(domainCode = DOMAIN_CODE)
@MapperScan(basePackages = "com.juphoon.rtc.datacenter.mapper")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
