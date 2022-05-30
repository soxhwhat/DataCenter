package com.juphoon.rtc.datacenter.cube.service;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.*;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.*;
import static com.juphoon.rtc.datacenter.api.EventType.LOG_EVENT;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/4/8 10:00
 * @Description:
 */
@Slf4j
//@Component
public class LogCollectionServerProcess extends AbstractServerProcess<String> {

    @Override
    public List<EventContext> trans(List<String> from, String host, String magic) throws Exception {
        EventContext ec = new EventContext();
        Map<String, Object> map = new HashMap<>(16);
        for (int i = 0; i < from.size(); i++) {
            map.put(String.valueOf(i), parseLog(from.get(i)));
        }
        Event t = trans(map);
        ec.setRequestId(t.getUuid());
        ec.setEvent(t);
        ec.setFrom(host);
        return Arrays.asList(ec);
    }

    @Override
    public Event trans(String from) throws Exception {
        return null;
    }

    public Event trans(Map<String, Object> params) throws Exception {
        return Event.builder()
                .number(LOG_EVENT.getNumber())
                .type(LOG_EVENT.getType())
                .params(params)
                .uuid(UUID.randomUUID().toString().replaceAll("-", "")).build();
    }

    public String parseLog(String logInfo) {
        JSONObject logObject = new JSONObject();
        try {
            JSONObject sourceLog = new JSONObject(logInfo);
            Long timestamp = sourceLog.getLong(TIMESTAMP);
            sourceLog.remove(TIMESTAMP);
            logObject.put(UNDERLINE_TIMESTAMP, timestamp);
            logObject.put(UNDERLINE_VERSION_KEY, UNDERLINE_VERSION_VALUE);
            logObject.put(VERSION_SOURCE, sourceLog);
        } catch (JSONException e) {
            log.error("日志记录错误:{}", logInfo);
            return null;
        }
        return logObject.toString();
    }
}
