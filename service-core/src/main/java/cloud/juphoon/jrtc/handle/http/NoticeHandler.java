package cloud.juphoon.jrtc.handle.http;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.EventType;
import cloud.juphoon.jrtc.config.HttpRequestParam;
import cloud.juphoon.jrtc.processor.NoticeHttpProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cloud.juphoon.jrtc.api.EventType.*;


/**
 * <p>验证码 验证加入房间 进出房间 录制文件 等事件放在一个handler中</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/3 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
public class NoticeHandler extends DefaultNoticeEventHandler {


    public NoticeHandler(NoticeHttpProcessor noticeHttpProcessor) {
        this.noticeHttpProcessor = noticeHttpProcessor;
        log.info("noticeHandler");
    }

    NoticeHttpProcessor noticeHttpProcessor;


    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.VERIFY_CODE, EventType.VERIFY_JOIN);
    }


    @Override
    public HttpRequestParam<Map<String, Object>, RestTemplate> preHandle(EventContext t) throws Exception {
        EventType eventType = t.getEvent().getEventType();
        HttpRequestParam<Map<String, Object>, RestTemplate> httpRequestParam = new HttpRequestParam<Map<String, Object>, RestTemplate>();
        //设置父类需要的template 以及 uri
        httpRequestParam.setTemplate(noticeHttpProcessor.getTemplate());
        httpRequestParam.setHost(noticeHttpProcessor.getConfig().getUri());
        //这里理论上不需要二度care，DisruptorEventHandle处有care
        //这里if 很多很难看，如果想新增，可以考虑新增handler。
        if (!careEvents().contains(eventType)) {
            throw new Exception("no care event [" + eventType + "]");
        }
        if (eventType.equals(LOGIN_EVENT)) {
            httpRequestParam.setEndPoint("/userlogin_notify");
        } else if (eventType.equals(LOGOUT_EVENT)) {
            httpRequestParam.setEndPoint("/userlogout_notify");
        } else if (eventType.equals(VERIFY_CODE)) {
            httpRequestParam.setEndPoint("/userlogin_request");
            httpRequestParam.setParams(t.getEvent().getParams());
        } else if (eventType.equals(VERIFY_JOIN)) {
            httpRequestParam.setEndPoint("/prepare_enterroom");
            httpRequestParam.setParams(t.getEvent().getParams());
        } else if (eventType.equals(ROOM_NOTICE)) {
            httpRequestParam.setEndPoint("/room_notify");
            httpRequestParam.setParams(t.getEvent().getParams());
        } else if (eventType.equals(SNAPSHOT_NOTICE)) {
            httpRequestParam.setEndPoint("/recordsnapshot_notify");
            httpRequestParam.setParams(t.getEvent().getParams());
        }
        return httpRequestParam;
    }


}
