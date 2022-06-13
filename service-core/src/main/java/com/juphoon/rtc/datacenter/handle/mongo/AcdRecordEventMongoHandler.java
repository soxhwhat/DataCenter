package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.MONGO_TEMPLATE_RECORD;
import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * <p>录制事件处理handler</p>
 *
 * @author Yuan wenjun
 * @date 2022/04/28
 */
@Slf4j
@Component
public class AcdRecordEventMongoHandler extends AbstractMongoEventHandler {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
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
    public String collectionName(EventContext ec) {
        return "jrtc.cloud.recordingEvents";
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

}
