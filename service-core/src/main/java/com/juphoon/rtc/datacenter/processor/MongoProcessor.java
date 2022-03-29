package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.StatType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/3 16:03
 * @Description:
 */
@Component
public class MongoProcessor extends AbstractEventProcessor {
    public MongoProcessor() {
    }

    public MongoProcessor(MongoProcessor.Config config) {
        this.config = config;
    }

    private MongoProcessor.Config config;

    public static class Config {
        public Map<String, StatType> map = new HashMap<>();
    }
}
