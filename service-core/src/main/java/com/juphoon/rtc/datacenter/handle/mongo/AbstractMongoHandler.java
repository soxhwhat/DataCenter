package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.processor.MongoProcessor;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:37
 * @Description:
 */
public abstract class AbstractMongoHandler extends AbstractEventHandler {

    public MongoProcessor mongoProcessor;

    public MongoProcessor getMongoProcessor() {
        if (mongoProcessor == null) {
            mongoProcessor = (MongoProcessor) processor;
        }
        return mongoProcessor;
    }

    public MongoTemplate getMongoTemplate() {
        return getMongoProcessor().getMongoTemplate();
    }

    public String getCollectionName(EventContext ec) {
        return getMongoProcessor().getConfig().collectionMap.get(ec.getEvent().getType().toString());
    }

}
