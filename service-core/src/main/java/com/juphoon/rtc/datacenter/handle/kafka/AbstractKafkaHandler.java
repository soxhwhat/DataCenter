package com.juphoon.rtc.datacenter.handle.kafka;

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
    private KafkaTemplate kafkaTemplate;

    @Autowired
    protected KafkaProperties kafkaProperties;

    @Override
    public boolean handle(EventContext ec) {
        try {
            String topic = getTopic(ec);
            log.info("ec:{},topic:{}", ec, topic);
            kafkaTemplate.send(topic, ec.getEvent().toString()).get();
        } catch (KafkaException kafkaException) {
            return false;
        } catch (ExecutionException e) {
            log.error("kafkaExecutionException:", e);
            return false;
        } catch (InterruptedException e) {
            return false;
        }
        log.info("执行kafkaHandle结束");
        return true;
    }

    /**
     * 获取topic
     * @param ec
     * @return
     */
    abstract String getTopic(EventContext ec);
}
