package com.juphoon.rtc.datacenter.cube.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.iron.component.utils.response.IronException;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.entity.po.cube.InfoBO;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.juphoon.rtc.datacenter.api.EventType.LOG_EVENT;
import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.*;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/4/8 10:00
 * @Description:
 */
@Slf4j
@Component
public class InfoCollectionServerProcess extends AbstractServerProcess<InfoBO> {

    @Override
    public List<EventContext> trans(List<InfoBO> from, String host, String magic) throws
            JsonProcessingException {
        List<EventContext> ret = new ArrayList<>();
        for (InfoBO infoBO : from) {
            Event t = trans(infoBO);
            EventContext ec = new EventContext();
            ec.setRequestId(t.getUuid());
            ec.setEvent(t);
            ec.setFrom(host);
            ec.setMagic(magic);
            ret.add(ec);
        }
        return ret;
    }

    @Override
    public Event trans(InfoBO from) throws JsonProcessingException {
        Map<String, Object> params = new HashMap<>(1);
        params.put("info", parseInfo(from));
        return Event.builder()
                .type(EventType.INFO_EVENT.getType())
                .number(EventType.INFO_EVENT.getNumber())
                .timestamp(from.getTimestamp())
                .params(params)
                .uuid(from.getId())
                .build();
    }

    private String parseInfo(InfoBO from) {
        Document document = new Document();
        log.debug("{}", from);
        try {
            Object generate = new ObjectIdGenerator().generate();
            from.setId(generate.toString());
            document.put(VERSION_KEY, VERSION_VALUE);
            document.put(ID_KEY, generate);
            document.put(UNDERLINE_TIMESTAMP, from.getTimestamp());
            Document sourceJson = new Document();
            document.put(VERSION_SOURCE, sourceJson);
            document.put(TYPE, from.getType());
            sourceJson.put(TAGS, from.getTags());
            sourceJson.put(SOURCE_INFO_KEY, Document.parse(from.getParams()));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return document.toJson();
    }
}
