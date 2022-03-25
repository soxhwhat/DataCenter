package com.juphoon.rtc.datacenter.handle.http.agree;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/24 11:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
public class AgreeSendOnlineMessageHandler extends AbstractAgreeNoticeHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AgreeTransBufferNotifyHandler;
    }

    @Override
    public String endpoint() {
        return "/transbuffer_notify";
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(EventType.SEND_ONLINE);
    }

    @Override
    public Map<String, String> handleRequest(EventContext ec) {
        //noticeServiceImpl类中已经封装好了参数，这一版暂时先这样，后续定义新的rpc接口时再重新封装
        return ec.getEvent().getParams();
//        Map<String, String> params = ec.convertStringMap();
//        deleteRoomIdSuffix(params);
//        String sendUserUri = params.get("account");
//        sendUserUri = getUsername(sendUserUri);
//        params.put("account", sendUserUri);
//        String receiverUri = params.get("receiverUri");
//        TransbufferReq transbufferReq = new TransbufferReq();
//        String finalSendUserUri = sendUserUri;
//        sessionAgent.sendImOnlineMessage(receiverUri, params, ret -> {
//            if (ret) {
//                String json = IronJsonUtils.objectToJson(params);
//                String databuf = Base64Utils.encodeToString(json.getBytes());
//                transbufferReq.setDatesize(String.valueOf(databuf.length()));
//                transbufferReq.setUserid(finalSendUserUri);
//                transbufferReq.setUsername(finalSendUserUri);
//                transbufferReq.setDatabuf(databuf);
//                log.info("send notice..... {}", receiverUri);
//            }
//        });
//
//        return transbufferReq.convertToMap();
    }
}
