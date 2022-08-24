package com.juphoon.rtc.datacenter.test;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
import com.juphoon.rtc.def.domain.DomainCodeEnum;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>示例工程</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 */
@SpringBootApplication(scanBasePackages = "com.juphoon")
@EnableScheduling
@CubeStarterApplication(domainCode = DomainCodeEnum.DATA_CENTER, basePackages = "com.juphoon")
@MapperScan(basePackages = "com.juphoon.rtc.datacenter")
@EnableMongoRepositories(basePackages = "com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
