package com.juphoon.rtc.datacenter.servicecore.handle.mongo;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity.MongoMdEventPO;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_MD;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.*;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_MD;

/**
 * <p>终端埋点事件处理handler</p>
 *
 * @author ke.wang
 * @date 2022-04-21
 */
@Slf4j
@Component
public class MdEventMongoHandler extends AbstractMongoEventHandler<MongoMdEventPO> {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_MD)
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.MdEventMongoHandler;
    }

    @Override
    public MongoMdEventPO poFromEvent(Event event) {
        MongoMdEventPO md = new MongoMdEventPO();
        md.fromEvent(event);
        return md;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                MD_LOGIN_ERROR,
                MD_LOGIN_SUCCESS,
                MD_CALL_ERROR,
                MD_CALL_ESTABLISHED,
                MD_CALL_EXP_END,
                MD_CALL_SUCCESS,
                MD_RECORD_ERROR,
                MD_CAMERA_PERMISSION_ERROR,
                MD_GATEWAY_CONNECTION_ERROR,
                MD_BROWSER_REPORT_ERROR,
                MD_RENDER_ERROR,
                MD_MEDIA_ERROR,
                MD_AUDIO_ERROR,
                MD_MICROPHONE_PERMISSION_ERROR,
                SIP_MEDIA_EVENT,
                SIP_INVITE_EVENT,
                SIP_BYE_EVENT,
                SIP_ACK_EVENT,
                SIP_UPDATE_EVENT,
                SIP_OPTION_EVENT,
                SIP_OTHER_REQUEST_EVENT,
                SIP_100_EVENT,
                SIP_183_EVENT,
                SIP_200_EVENT,
                SIP_300_EVENT,
                SIP_400_EVENT,
                SIP_500_EVENT,
                SIP_600_EVENT,
                SIP_OTHER_RESPONSE_EVENT,
                CD_JOIN_ROOM_EVENT,
                CD_RECEIVE_START_SIGNAL_EVENT,
                CD_START_EVENT,
                CD_RECEIVE_END_SIGNAL_EVENT,
                CD_END_EVENT,
                CD_ABNORMAL_EVENT,
                CD_HEARTBEAT_EVENT,
                CD_RECEIVE_FIRST_FRAME_OF_MEDIA_DATA_EVENT,
                CD_LEAVE_ROOM_EVENT
        );
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_MD;
    }
}
