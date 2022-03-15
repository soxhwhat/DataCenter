package cloud.juphoon.jrtc.handle.http;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.EventType;
import cloud.juphoon.jrtc.config.HttpRequestParam;
import cloud.juphoon.jrtc.processor.NoticeHttpProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/3 15:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
public class SendOnlineHandler extends DefaultNoticeEventHandler {

    protected EventType sendOnline = new EventType(1, 5);

    NoticeHttpProcessor noticeHttpProcessor;

    public SendOnlineHandler(NoticeHttpProcessor noticeHttpProcessor) {
        this.noticeHttpProcessor = noticeHttpProcessor;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(sendOnline);
    }

    @Override
    public HttpRequestParam<Map<String, Object>, RestTemplate> preHandle(EventContext t) {
        HttpRequestParam<Map<String, Object>, RestTemplate> httpRequestParam = new HttpRequestParam<Map<String, Object>, RestTemplate>();
        httpRequestParam.setTemplate(noticeHttpProcessor.getTemplate());
        httpRequestParam.setHost(noticeHttpProcessor.getConfig().getUri());
        HashMap<String, Object> params = new HashMap<>();

        httpRequestParam.setParams(params);
        httpRequestParam.setEndPoint("/transbuffer_notify");
        return httpRequestParam;
    }


}
