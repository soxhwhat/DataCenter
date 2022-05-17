package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.ProcessorId;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/3 16:03
 * @Description:
 */
@Getter
@Component
public class MongoProcessor extends AbstractEventProcessor {
    @Override
    ProcessorId processorId() {
        return ProcessorId.MONGO;
    }
}
