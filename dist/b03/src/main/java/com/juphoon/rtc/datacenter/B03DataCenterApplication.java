package com.juphoon.rtc.datacenter;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>在开始处详细描述作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author  ke.wang@juphoon.com
 * @date   2022/4/27
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]       
 */
@SpringBootApplication
@EnableScheduling
@CubeStarterApplication
public class B03DataCenterApplication {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            args = new String[]{"b03.dataCenter"};
        }
        SpringApplication.run(B03DataCenterApplication.class, args);
    }
}
