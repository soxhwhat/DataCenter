package com.juphoon.rtc.datacenter.servicecore.handle.mongo;

import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.*;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_B03_RECORDS;

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
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public MongoCollectionEnum collectionName() {
        /// TODO 产品考虑重新规划
        return COLLECTION_B03_RECORDS;
    }
}
