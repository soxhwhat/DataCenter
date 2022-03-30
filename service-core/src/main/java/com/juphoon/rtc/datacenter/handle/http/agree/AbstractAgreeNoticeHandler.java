package com.juphoon.rtc.datacenter.handle.http.agree;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.AbstractHttpEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.URL_PARAMS;


/**
 * <p>赞同业务抽象类</p>
 *
 * @author ajian.zheng
 * @date 2022-03-22
 */
@Slf4j
public abstract class AbstractAgreeNoticeHandler extends AbstractHttpEventHandler {
    /**
     * 指定 handler 访问的 endpoint
     *
     * @return
     */
    public abstract String endpoint();

//    /**
//     * 处理请求
//     *
//     * @param ec
//     * @return
//     */
//    public abstract Map<String, String> handleRequest(EventContext ec);

    @SuppressFBWarnings(value = {"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"}, justification = "fixed")
    @Override
    public boolean exchange(String url, RestTemplate restTemplate, EventContext ec) {
        boolean ret = true;

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url + endpoint() + URL_PARAMS).encode().toUriString();
        String reqId = UUID.randomUUID().toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // 生成请求参数
            Map<String, String> params = ec.getEvent().getParamsCopy();

            assert null != params : "请求参数为空";

            /// 1. 风格保持赞同接口统一
            /// 2. 方便问题定位沟通请求id
            params.put("reqid", reqId);

            HttpEntity entity = new HttpEntity<>(params, headers);

            log.info("req:{}", entity);

            ResponseEntity<AgreeResponse> response
                    = restTemplate.exchange(urlTemplate, HttpMethod.POST, entity, AgreeResponse.class);

            log.info("reqId:{}, status:{}", reqId, response.getStatusCode());

            if (null != response.getBody()) {
                log.info("reqId:{}, code:{}, msg:{}",
                        reqId,
                        null == response.getBody().getReturnCode() ? "" : response.getBody().getReturnCode(),
                        null == response.getBody().getReturnMsg() ? "" : response.getBody().getReturnMsg());
            }
        } catch (RestClientException e) {
            if (log.isDebugEnabled()) {
                log.debug("", e);
            }
            ret = false;
        }

        return ret;
    }

    @Data
    private static class AgreeResponse {
        @JsonProperty("return_code")
        private String returnCode;

        @JsonProperty("return_msg")
        private String returnMsg;
    }

}
