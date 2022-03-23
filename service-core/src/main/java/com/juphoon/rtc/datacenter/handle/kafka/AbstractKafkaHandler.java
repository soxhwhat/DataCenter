package com.juphoon.rtc.datacenter.handle.kafka;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.processor.KafkaProcessor;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:51
 * @Description:
 */
public abstract class AbstractKafkaHandler extends AbstractEventHandler {

    public DataListenableFutureCallback callback = new DataListenableFutureCallback();

    public KafkaProcessor kafkaProcessor;

    public KafkaProcessor getKafkaProcessor() {
        if (kafkaProcessor == null) {
            kafkaProcessor = (KafkaProcessor) processor;
        }
        return kafkaProcessor;
    }

    public KafkaTemplate getKafkaTemplate() {
        return getKafkaProcessor().getKafkaTemplate();
    }

    public String getTopic(EventContext ec) {
        return getKafkaProcessor().getConfig().collectionMap.get(ec.getEvent().getType().toString());
    }

}
