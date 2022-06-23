package com.juphoon.rtc.datacenter;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
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
@SpringBootApplication(scanBasePackages = "com.juphoon.rtc.datacenter")
@CubeStarterApplication
@MapperScan(basePackages = "com.juphoon.rtc.datacenter.mapper")
@EnableMongoRepositories(basePackages = "com.juphoon.rtc.datacenter.handle.mongo.entity")
public class ServerCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerCoreApplication.class, args);
    }
}
