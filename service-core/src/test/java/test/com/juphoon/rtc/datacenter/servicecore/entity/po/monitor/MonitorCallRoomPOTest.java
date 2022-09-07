package test.com.juphoon.rtc.datacenter.servicecore.entity.po.monitor;


import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorCallRoomPO;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MonitorCallRoomPOTest {


    /**
     * 转换: * FlowStatusJson(timestamp=1658200991816, type=23, eventNumber=2, uuid=, domainId=100645, appId=4, params={
     *    "appId" : "4",
     *    "beginTimestamp" : 1658200811719,
     *    "callId" : "108486620719112011",
     *    "domainId" : "100645",
     *    "endTimestamp" : 1658200991816,
     *    "reason" : "",
     *    "roomId" : "gvdf"
     * })
     */
    @SneakyThrows
    @Test
    public void fromEvent() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("beginTimestamp", 1658200813102L);
        map.put("endTimestamp", 1658200863847L);
        map.put("callId", "108486620719112011");
        map.put("domainId", 100645);
        map.put("appId", 4);
        map.put("reason", "leave");
        map.put("roomId", "gvdf");


        Event event = Event.builder()
                .domainId(100645)
                .appId(4)
                .type(23)
                .number(98)
                .timestamp(1658222801572L)
                .params(map)
                .uuid("62d678d1e23bd80a5e0b7199")
                .build();

        EventContext ec = new EventContext(event);

        MonitorCallRoomPO po = MonitorCallRoomPO.fromEvent(ec);

        Assert.assertEquals(1658200813102L, po.getBeginTimestamp().longValue());
        Assert.assertEquals(1658200863847L, po.getEndTimestamp().longValue());
        Assert.assertEquals("108486620719112011", po.getCallId());
        Assert.assertEquals(100645, po.getDomainId().intValue());
        Assert.assertEquals(4, po.getAppId().intValue());
        Assert.assertEquals("leave", po.getReason());
        Assert.assertEquals("gvdf", po.getRoomId());

    }
}