package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.StatType;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 10:39 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Getter
@Component
@Scope("prototype")
public class DatabaseEventProcessor extends AbstractEventProcessor {
    public DatabaseEventProcessor() {
    }

    public DatabaseEventProcessor(Config config) {
        this.config = config;
    }

    private DatabaseEventProcessor.Config config;

    public static class Config {
        public Map<String, StatType> map = new HashMap<>();

    }

}
