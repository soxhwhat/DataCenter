package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.TICKER_COMPLETE;

/**
 * <p>客服话单事件处理handler</p>
 *
 * @author Yuan wenjun
 * @date 2022/04/28
 */
@Slf4j
@Component
public class AcdTicketEventMongoHandler extends AbstractB03MongoHandler {

    @Autowired
    @Qualifier("recordMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdTicketEventMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(TICKER_COMPLETE);
    }

    @Override
    public String collectionName() {
        return "jrtc.records";
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

}
