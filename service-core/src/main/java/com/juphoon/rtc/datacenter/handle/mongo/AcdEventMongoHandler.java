package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * <p>客服事件处理handler</p>
 *
 * @author ajian.zheng
 * @date 2022-03-29
 */
@Slf4j
@Component
public class AcdEventMongoHandler extends AbstractMongoHandler {

    @Autowired
    @Qualifier("eventMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdEventMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                TICKER_EVENT_WAIT,
                TICKER_EVENT_RING,
                TICKER_EVENT_TALK,
                TICKER_EVENT_OVERFLOW,
                TICKER_EVENT_TRANSFER,
                TICKER_EVENT_INVITE_AGENT,
                AGENT_OP_EVENT_BUSY,
                AGENT_OP_EVENT_FREE,
                AGENT_OP_EVENT_KEEP,
                AGENT_OP_EVENT_LOGIN
        );
    }

    @Override
    public String collectionName(EventContext ec) {
        return "jrtc.events_" + DateFormatUtils.format(new Date(ec.getCreatedTimestamp()), "yyyyMMdd");
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }
}
