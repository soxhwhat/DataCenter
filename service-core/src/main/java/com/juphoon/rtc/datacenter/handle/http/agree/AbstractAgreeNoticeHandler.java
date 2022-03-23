package com.juphoon.rtc.datacenter.handle.http.agree;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.AbstractHttpEventHandler;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;


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

    /**
     * 处理请求
     *
     * @param ec
     * @return
     */
    public abstract Map<String, Object> handleRequest(EventContext ec);

    @Override
    public boolean exchange(String url, RestTemplate restTemplate, EventContext ec) {
        boolean ret = true;

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url + endpoint()).encode().toUriString();
        String reqId = UUID.randomUUID().toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // 生成请求参数
            Map<String, Object> params = handleRequest(ec);

            assert null != params : "请求参数为空";

            params.put("reqid", reqId);

            HttpEntity entity = new HttpEntity<>(params, headers);

            log.info("req:{}", entity);

            ResponseEntity<AgreeResponse> response
                    = restTemplate.exchange(urlTemplate, HttpMethod.POST, entity, AgreeResponse.class);

            log.info("reqId:{}, status:{}", reqId, response.getStatusCode());

            if (null != response.getBody()) {
                log.info("reqId:{}, code:{}, msg:{}",
                        reqId,
                        response.getBody().getReturnCode(),
                        response.getBody().getReturnMsg());
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
