package com.juphoon.rtc.datacenter.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>测试类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/18/22 10:02 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@Component
@ConfigurationProperties(prefix = "demo")
public class TestConfig {
    private String test;

}
