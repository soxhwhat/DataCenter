package cloud.juphoon.jrtc.handle.http;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.config.HttpRequestParam;
import cloud.juphoon.jrtc.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static cloud.juphoon.jrtc.constant.JrtcDataCenterConstant.URL_PARAMS;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/3 17:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public abstract class DefaultNoticeEventHandler extends AbstractEventHandler implements IHttpEventHandler<HttpRequestParam<Map<String, Object>, RestTemplate>, HttpEntity, RestTemplate> {

    public DefaultNoticeEventHandler() {
        log.info("DefaultNoticeEventHandler");
    }

    @Override
    public boolean handle(EventContext ec) throws Exception {
        HttpRequestParam<Map<String, Object>, RestTemplate> httpRequestParam = preHandle(ec);

        HttpEntity httpEntity = postHandle(httpRequestParam, httpRequestParam.getTemplate());
        afterComplete(httpEntity);
        return true;
    }

    @Override
    public HttpEntity postHandle(HttpRequestParam<Map<String, Object>, RestTemplate> param, RestTemplate restTemplate) {
        ResponseEntity<Map> httpEntity = restTemplate.postForEntity(param.getHost() + param.getEndPoint() + URL_PARAMS, param.getParams(), Map.class);
        return httpEntity;
    }


    @Override
    public void afterComplete(HttpEntity httpEntity) {
        log.info("httpEntity:{}", httpEntity);
    }


}
