package com.juphoon.rtc.datacenter.servicecore.handle.mongo;

import com.juphoon.rtc.datacenter.datacore.api.*;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.ROOM_BEAT;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_CONCURRENT_ITEM_ROOM;

/**
 * 并发事件数据处理
 *
 * @author zhiwei.zhai
 * @date 2022-05-31
 */
@Slf4j
@Component
public class ConcurrentMongoHandler extends AbstractMongoHandler<StateContext> {

    @Autowired
    @Qualifier("eventMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.ConcurrentMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                ROOM_BEAT
        );
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_CONCURRENT_ITEM_ROOM;
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public boolean handle(StateContext context) throws Exception {
        /// TODO
        return false;
    }
}
