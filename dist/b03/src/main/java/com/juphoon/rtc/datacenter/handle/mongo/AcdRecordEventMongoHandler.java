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
 * <p>录制事件处理handler</p>
 *
 * @author Yuan wenjun
 * @date 2022/04/28
 */
@Slf4j
@Component
public class AcdRecordEventMongoHandler extends AbstractB03MongoHandler {

    @Autowired
    @Qualifier("eventMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdRecordEventMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
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
    public String collectionName() {
        return "jrtc.cloud.recordingEvents";
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

}
