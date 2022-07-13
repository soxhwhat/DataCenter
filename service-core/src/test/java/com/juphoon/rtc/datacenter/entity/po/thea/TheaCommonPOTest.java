package com.juphoon.rtc.datacenter.entity.po.thea;


import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
public class TheaCommonPOTest {
    EventContext ec;
    Event event;

    @Before
    public void initEc() {
        log.info("init ec");
        ec = TheaEventContextUtils.getEventContext();
        HashMap<String, Object> params = null;
        event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(900)
                .number(0)
                .timestamp(System.currentTimeMillis())
                .params(params)
                .uuid(UUID.randomUUID().toString())
                .build();
        log.info("ec: {}", ec);
    }

//    @Test(expected = InvalidParameterException.class)
//    public void commonCheckParamException() {
//        TheaCommonPO.commonCheckParam(event);
//    }

    @Test
    public void commonCheckParam() {
        TheaCommonPO.commonCheckParam(ec.getEvent());
    }
}