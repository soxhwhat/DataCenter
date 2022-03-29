package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/3 16:05
 * @Description:
 */
@Slf4j
@Component
public class EventMongoHandler extends AbstractMongoHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.EventMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                TICKER_STATUS_WAIT,
                TICKER_STATUS_RING,
                TICKER_STATUS_TALK,
                TICKER_STATUS_OVERFLOW,
                TICKER_STATUS_TRANSFER,
                TICKER_STATUS_INVITE_AGENT,
                STAFF_STATUS_BUSY,
                STAFF_STATUS_FREE,
                STAFF_STATUS_KEEP,
                STAFF_STATUS_LOGIN,
                TICKER_COMPLETE,
                STAFF_BEAT,
                QUEUE_BEAT);
    }

    @Override
    public boolean handle(EventContext ec) {
        try {
            String collectionName = getCollectionName(ec);
            log.info("ec:{},collectionName:{}", ec, collectionName);
            getMongoTemplate().insert(ec.getEvent(), collectionName);
        } catch (DataAccessException e) {
            log.error("DataAccessException:{}",e);
            return false;
        } catch (Exception e) {
            log.error("{}", e);
            return true;
        }
        log.info("执行MongoHandle结束");
        return true;
    }


}
