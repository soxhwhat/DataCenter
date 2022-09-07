package test.com.juphoon.rtc.datacenter.servicecore.entity.po.monitor;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorCallUserPO;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MonitorCallUserPOTest {

    /**
     * 转换数据上报: * FlowStatusJson(timestamp=1658200863847, type=23, eventNumber=98, uuid=, domainId=100645, appId=4, params={
     *    "appId" : "4",
     *    "beginTimestamp" : 1658200813102,
     *    "callId" : "108486620719112011",
     *    "domainId" : "100645",
     *    "endTimestamp" : 1658200863847,
     *    "reason" : "leave",
     *    "roomId" : "gvdf",
     *    "userId" : "[username:testmy01@100645.cloud.justalk.com]"
     *    })
     */
    @SneakyThrows
    @Test
    public void fromEvent() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("beginTimestamp", 1658200811719L);
        map.put("callId", "108486620719112011");
        map.put("domainId", 100645);
        map.put("appId", 4);
        map.put("endTimestamp", 1658200991816L);
        map.put("roomId", "gvdf");
        map.put("reason", "111");
        map.put("userId", "[username:testmy01@100645.cloud.justalk.com]");


        Event event = Event.builder()
                .domainId(100645)
                .appId(4)
                .type(23)
                .number(2)
                .timestamp(1658222801572L)
                .params(map)
                .uuid("62d678d1e23bd80a5e0b7199")
                .build();

        EventContext ec = new EventContext(event);

        MonitorCallUserPO po = MonitorCallUserPO.fromEvent(ec);

        Assert.assertEquals(1658200811719L, po.getBeginTimestamp().longValue());
        Assert.assertEquals("108486620719112011", po.getCallId());
        Assert.assertEquals(100645, po.getDomainId().intValue());
        Assert.assertEquals(4, po.getAppId().intValue());
        Assert.assertEquals(1658200991816L, po.getEndTimestamp().longValue());
        Assert.assertEquals("gvdf", po.getRoomId());
        Assert.assertEquals("111", po.getReason());
        Assert.assertEquals("testmy01", po.getUserId());

    }
}