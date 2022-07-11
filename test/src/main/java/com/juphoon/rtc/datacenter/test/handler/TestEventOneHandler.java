package com.juphoon.rtc.datacenter.test.handler;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.handle.mongo.AbstractMongoEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.api.MongoCollectionEnum.COLLECTION_EVENT_TEST_DAILY;

/**
 * @author ajian.zheng@juphoon.com
 * @date 2/16/22 10:19 AM
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
public class TestEventOneHandler extends AbstractMongoEventHandler {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.TestEventOneHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.values());
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_EVENT_TEST_DAILY;
    }

    @Override
    public boolean collectionStorageDaily() {
        return true;
    }
}
