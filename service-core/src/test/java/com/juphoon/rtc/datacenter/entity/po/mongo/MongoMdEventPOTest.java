package com.juphoon.rtc.datacenter.entity.po.mongo;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handle.mongo.entity.MongoMdEventPO;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wenjun.yuan@juphoon.com
 * @date 7/15/22 15:26
 */
public class MongoMdEventPOTest {

    /**
     * 转换
     * {
     * 	"traceId" : "3c0d8c46-57ba-4323-b305-ff93f61f483c",
     * 	"platform" : "Android",
     * 	"channel" : "channel:1657870044601",
     * 	"sdkVersion" : "2203.0.6(202207151)",
     * 	"username" : "lzz",
     * 	"businessId" : "BusinessId:1657870064987",
     * 	"callId" : "101226770715152935",
     * 	"appKey" : "05c97ce0d0a84d2592334093",
     * 	"paramsType" : 2,
     * 	"paramsResult" : 1,
     * 	"content" : "{desc=setForeground:false}",
     * 	"domainId" : 100645,
     * 	"appId" : 4,
     * 	"uuid" : "b121979d-5470-4439-a8af-112137906a1a",
     * 	"type" : 1000,
     * 	"number" : 13,
     * 	"timestamp" : 1657871993132,
     * }
     */
    @Test
    public void fromEventOk() {
        Map<String, Object> map = new HashMap(8);
        map.put("traceId", "traceId");
        map.put("domainId", 100645);
        map.put("appId", 4);
        map.put("platform", "platform");
        map.put("channel", "channel");
        map.put("callId", "1ddac265-d4cd-49a8-86f0-730dcd2ebd34");
        map.put("sipCallId", "1ddac265-d4cd-49a8-86f0-730dcd2ebd36");
        map.put("sdkVersion", "a6");
        map.put("browser", "b5");
        map.put("businessId", "b5");
        map.put("username", "b5");
        map.put("type", 1);
        map.put("result", 0);
        map.put("content", "content");
        map.put("cdAccount", "50000");
        map.put("filename", "703c2e77-3e78-4b1b-8c12-70ebf5372504");
        map.put("appKey", "acd");

        Event event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(1200)
                .number(1)
                .timestamp(System.currentTimeMillis()+100000000)
                .params(map)
                .uuid(UUID.randomUUID().toString())
                .build();
        EventContext ec = new EventContext(event);

        MongoMdEventPO md = new MongoMdEventPO();
        md.fromEvent(ec.getEvent());

        Assert.assertEquals(1200, md.getType().longValue());
        Assert.assertEquals("1ddac265-d4cd-49a8-86f0-730dcd2ebd34", md.getCallId());
        Assert.assertEquals(1, md.getParamsType().intValue());
        Assert.assertEquals("50000", md.getCdAccount());
    }

}

