package cloud.juphoon.jrtc.handle.http;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.config.HttpRequestParam;
import cloud.juphoon.jrtc.handler.AbstractEventHandler;
import cloud.juphoon.jrtc.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

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
public abstract class DefaultNoticeEventHandler extends AbstractEventHandler implements IHttpEventHandler<HttpRequestParam<Map<String, Object>, HttpClientUtil>, String, HttpClientUtil> {

    public DefaultNoticeEventHandler() {
        log.info("DefaultNoticeEventHandler");
    }

    @Override
    public boolean handle(EventContext ec) throws Exception {
        HttpRequestParam<Map<String, Object>, HttpClientUtil> httpRequestParam = preHandle(ec);
        String result = postHandle(httpRequestParam, httpRequestParam.getTemplate());
        afterComplete(result);
        return true;
    }

    @Override
    public String postHandle(HttpRequestParam<Map<String, Object>, HttpClientUtil> param, HttpClientUtil restTemplate) {
        return restTemplate.post(param.getHost() + param.getEndPoint() + URL_PARAMS, param.getParams(), null);
    }


    @Override
    public void afterComplete(String result) {
        log.info("httpEntity:{}", result);
    }


}
