package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * <p>客服事件处理handler</p>
 *
 * @author Yuan wenjun
 * @date 2022/04/28
 */
@Slf4j
@Component
public class B03AcdEventMongoHandler extends B03AbstractMongoHandler {

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
    public String collectionName() {
        return "jrtc.events";
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    protected Map<String, String> checkIndex(Set<String> indexKeyMap) {
        Map<String, String> indexMap = new HashMap<>(1);
        if (!indexKeyMap.contains(FIELD_PARAMS_CALLID)) {
            indexMap.put(collectionName(), FIELD_PARAMS_CALLID);
        }
        return indexMap;
    }

}
