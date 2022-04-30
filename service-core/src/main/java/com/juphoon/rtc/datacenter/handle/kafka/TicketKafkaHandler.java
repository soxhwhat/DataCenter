package com.juphoon.rtc.datacenter.handle.kafka;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.handle.mongo.AbstractMongoHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * 话单状态上报处理器
 *
 * @author zhiwei.zhai
 * @date 2022-04-27
 */
@Slf4j
@Component
public class TicketKafkaHandler extends AbstractKafkaHandler {

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(TICKER_EVENT_WAIT, TICKER_EVENT_RING
                , TICKER_EVENT_TALK, TICKER_EVENT_OVERFLOW
                , TICKER_EVENT_TRANSFER, TICKER_EVENT_INVITE_AGENT
                , AGENT_OP_EVENT_BUSY, AGENT_OP_EVENT_FREE, AGENT_OP_EVENT_KEEP, AGENT_OP_EVENT_LOGIN
                , TICKER_COMPLETE);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.TicketKafkaHandler;
    }

    @Override
    String getTopic(EventContext ec) {
        return kafkaProperties.getTopic().getTicket();
    }
}
