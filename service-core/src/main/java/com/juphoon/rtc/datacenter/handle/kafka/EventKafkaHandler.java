package com.juphoon.rtc.datacenter.handle.kafka;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;

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
public class EventKafkaHandler extends AbstractKafkaHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.EventKafkaHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(TICKER_STATUS_WAIT, TICKER_STATUS_RING
                ,TICKER_STATUS_TALK,TICKER_STATUS_OVERFLOW
                ,TICKER_STATUS_TRANSFER,TICKER_STATUS_INVITE_AGENT
                ,STAFF_STATUS_BUSY,STAFF_STATUS_FREE,STAFF_STATUS_KEEP,STAFF_STATUS_LOGIN
                ,TICKER_COMPLETE,STAFF_BEAT,QUEUE_BEAT);
    }

    @Override
    public boolean handle(EventContext ec) {
        try {
            String topic = getTopic(ec);
            log.info("ec:{},topic:{}",ec,topic);
            getKafkaTemplate().send(topic, ec.getEvent().toString()).get();
        } catch (KafkaException kafkaException) {
            return false;
        } catch (ExecutionException e) {
            log.error("kafkaExecutionException : {}" ,e);
            return false;
        } catch (InterruptedException e) {
            return false;
        }
        log.info("执行kafkaHandle结束");
        return true;
    }
}
