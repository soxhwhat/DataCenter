package com.juphoon.rtc.datacenter.handle.kafka;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 9:45
 * @Description:
 */
@Slf4j
@Configuration
public class EventKafkaHandler extends AbstractKafkaHandler {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.EventKafkaHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(TICKER_EVENT_WAIT, TICKER_EVENT_RING
                , TICKER_EVENT_TALK, TICKER_EVENT_OVERFLOW
                , TICKER_EVENT_TRANSFER, TICKER_EVENT_INVITE_AGENT
                , AGENT_OP_EVENT_BUSY, AGENT_OP_EVENT_FREE, AGENT_OP_EVENT_KEEP, AGENT_OP_EVENT_LOGIN
                , TICKER_COMPLETE, STAFF_BEAT, QUEUE_BEAT);
    }

    @SuppressWarnings("PMD")
    @Override
    public boolean handle(EventContext ec) {
        /*
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
        */
        log.info("执行kafkaHandle结束");
        return true;
    }
}
