package com.juphoon.rtc.datacenter.handle;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.handler.AbstractHttpEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author ajian.zheng@juphoon.com
 * @date 3/21/22 8:52 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public class TestHttpEventHandler extends AbstractHttpEventHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.TestEventHandler;
    }

    private static final String END_POINT = "/test";

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(EventType.TEST_EVENT);
    }

    @Override
    public boolean exchange(String url, RestTemplate restTemplate, EventContext ec) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url + "/" + END_POINT)
                .queryParam("a", ec.getEvent().eventType())
                .queryParam("b", "你好")
                .queryParam("c", "./)(*JJ<>")
                .encode()
                .toUriString();

        log.info("url:{}", urlTemplate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HashMap<String, Object> params = new HashMap<>(1);
        params.put("name", "IronMain");

        HttpEntity entity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);

        log.info("status:{}", response.getStatusCode());
        log.info("body:{}", response.getBody());

        return true;
    }

    public static void main(String[] args) {
        String url = "/test";
        String urlTemplate = UriComponentsBuilder.fromUriString(url)
                .queryParam("a", "zhangsan")
                .queryParam("b", "你好")
                .queryParam("c", "./)(*JJ<>")
                .encode()
                .toUriString();
        System.out.println(urlTemplate);
    }
}
