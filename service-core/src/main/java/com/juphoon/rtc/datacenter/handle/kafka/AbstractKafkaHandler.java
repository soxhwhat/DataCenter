package com.juphoon.rtc.datacenter.handle.kafka;

import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import com.juphoon.rtc.datacenter.property.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 9:45
 * @Description:
 */
@Slf4j
@Configuration
public abstract class AbstractKafkaHandler extends AbstractEventHandler {

    @Autowired
    protected KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    protected KafkaProperties kafkaProperties;

    @Override
    public boolean handle(EventContext ec) {
        try {
            String topic = getTopic(ec);
            String json = IronJsonUtils.objectToJson(getData(ec));
            kafkaTemplate.send(topic, json).get();
        } catch (KafkaException kafkaException) {
            return false;
        } catch (ExecutionException e) {
            log.error("kafkaExecutionException:", e);
            return false;
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    /**
     * 获取topic
     * @param ec
     * @return
     */
    abstract String getTopic(EventContext ec);

    /**
     * 获取数据
     * @param ec
     * @return
     */
    abstract Object getData(EventContext ec);

}
