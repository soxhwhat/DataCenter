package com.juphoon.rtc.datacenter.processor.impl;

import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import org.springframework.stereotype.Component;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    2/18/22 10:39 AM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
public class MySqlEventProcessor extends AbstractEventProcessor {
    public MySqlEventProcessor() {
    }

    public MySqlEventProcessor(Config config) {
    }


    public static class Config {
        private String username;

        private String password;

    }
}