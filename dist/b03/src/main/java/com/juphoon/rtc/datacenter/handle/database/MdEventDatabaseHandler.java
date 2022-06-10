package com.juphoon.rtc.datacenter.handle.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.entity.MdEventPO;
import com.juphoon.rtc.datacenter.mapper.MdEventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * <p>埋点数据handler</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/5/10 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
public class MdEventDatabaseHandler extends AbstractDatabaseHandler<MdEventPO> {

    @Autowired
    private MdEventMapper mdEventMapper;


    @Override
    public MdEventPO preData(Event event) {
        log.info("preData:{}", event.getParams());
        Map<String, Object> params = event.getParams();
        ObjectMapper objectMapper = new ObjectMapper();
        MdEventPO mdEvent = objectMapper.convertValue(params, MdEventPO.class);
        mdEvent.setEventTime(event.getTimestamp());
        mdEvent.setEventNumber(event.getNumber());
        return mdEvent;
    }

    @Override
    public Integer insertSelective(MdEventPO event) {
        return mdEventMapper.insert(event);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.MdEventDatabaseHandler;
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
}
