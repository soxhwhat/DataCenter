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

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.MONGO_TEMPLATE_MD;
import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * <p>终端埋点事件处理handler</p>
 * todo 移植到产品
 *
 * @author ke.wang
 * @date 2022-04-21
 */
@Slf4j
@Component
public class MdEventMongoHandler extends AbstractMongoEventHandler {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_MD)
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.MdEventMongoHandler;
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
                SIP_OTHER_RESPONSE_EVENT
        );
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public String collectionName(EventContext ec) {
        // TODO 常量
        return "jrtc.md.events";
    }
}
