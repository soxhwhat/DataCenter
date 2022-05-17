package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.ProcessorId;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 9:33
 * @Description:
 */
@Getter
@Component
public class KafkaProcessor extends AbstractEventProcessor {
    @Override
    ProcessorId processorId() {
        return ProcessorId.KAFKA;
    }
}
