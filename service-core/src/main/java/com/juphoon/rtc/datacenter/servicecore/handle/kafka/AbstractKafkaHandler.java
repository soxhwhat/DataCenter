package com.juphoon.rtc.datacenter.servicecore.handle.kafka;

import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.handler.AbstractEventHandler;
import com.juphoon.rtc.datacenter.servicecore.property.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;

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
