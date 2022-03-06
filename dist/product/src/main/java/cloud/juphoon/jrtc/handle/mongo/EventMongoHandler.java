package cloud.juphoon.jrtc.handle.mongo;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.EventType;
import cloud.juphoon.jrtc.handler.AbstractEventHandler;
import cloud.juphoon.jrtc.process.MongoProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/3 16:05
 * @Description:
 */
@Slf4j
public class EventMongoHandler extends AbstractEventHandler {

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.TICKET_EVENT);
    }

    @Override
    public boolean handle(EventContext ec) {
        MongoProcessor mongoProcessor = (MongoProcessor) processor;
        MongoTemplate mongoTemplate = mongoProcessor.getMongoTemplate();
        MongoProcessor.Config config = mongoProcessor.getConfig();
        String collectionName = config.collectionMap.get(ec.getEvent().getType());
        try {
            mongoTemplate.insert(ec.getEvent(), collectionName);
        } catch (DataAccessException e) {
            return false;
        } catch (Exception e) {
            log.error("{}", e);
            return true;
        }
        log.info("执行MongoHandle结束");
            return true;
    }


}
