package com.juphoon.rtc.datacenter.processor;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 9:33
 * @Description:
 */
@Getter
@Component
public class KafkaProcessor extends AbstractEventProcessor {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public KafkaProcessor() {
    }

    public KafkaProcessor(KafkaProcessor.Config config) {
        this.config = config;
    }

    private KafkaProcessor.Config config;

    public static class Config {
        public Map<Integer, String> collectionMap = new HashMap<>();
    }

}
