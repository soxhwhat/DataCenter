package cloud.juphoon.jrtc.handle.mongo;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.handler.AbstractEventHandler;
import cloud.juphoon.jrtc.processor.MongoProcessor;
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
