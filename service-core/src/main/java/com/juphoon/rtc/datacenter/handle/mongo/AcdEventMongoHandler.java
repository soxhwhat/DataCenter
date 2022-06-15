package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.MongoCollectionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.api.EventType.*;
import static com.juphoon.rtc.datacenter.api.MongoCollectionEnum.COLLECTION_EVENT_PREFIX;

/**
 * <p>客服事件处理handler</p>
 *
 * @author ajian.zheng
 * @date 2022-03-29
 */
@Slf4j
@Component
public class AcdEventMongoHandler extends AbstractMongoEventHandler implements IMongoCollectionManager {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
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
                AGENT_OP_EVENT_LOGIN,
                RECORD_START,
                RECORD_STOP,
                RECORD_FILE_CREATE,
                RECORD_JOIN_MEETING_SUCCESS,
                RECORD_JOIN_MEETING_FAIL,
                RECORD_LEAVE_MEETING,
                RECORD_ERROR,
                JSMS_REBOOT,
                JMDS_REBOOT,
                CD_ERROR_EXIT
        );
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_EVENT_PREFIX;
    }

    @Override
    public boolean collectionStorageDaily() {
        return true;
    }
}
