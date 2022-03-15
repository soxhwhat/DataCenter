package cloud.juphoon.jrtc.handle.mongo;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/3 16:05
 * @Description:
 */
@Slf4j
@Component
public class EventMongoHandler extends AbstractMongoHandler {

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.TICKET_EVENT);
    }

    @Override
    public boolean handle(EventContext ec) {
        try {
            getMongoTemplate().insert(ec.getEvent(), getCollectionName(ec));
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
