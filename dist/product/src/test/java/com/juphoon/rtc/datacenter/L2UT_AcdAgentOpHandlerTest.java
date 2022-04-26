package com.juphoon.rtc.datacenter;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdAgentOpCheckinDailyPO;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdAgentOpStatDailyPO;
import com.juphoon.rtc.datacenter.handle.database.acdstat.AcdAgentOpCheckinDailyByShiftHandler;
import com.juphoon.rtc.datacenter.handle.database.acdstat.AcdAgentOpStatDailyHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/4/26 15:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class L2UT_AcdAgentOpHandlerTest {

    @Autowired
    private AcdAgentOpCheckinDailyByShiftHandler acdAgentOpCheckinDailyByShiftHandler;
    @Autowired
    private AcdAgentOpStatDailyHandler acdAgentOpStatDailyHandler;

    @Test
    public void poFromEventTest() {
        String agentId = "agent3";
        Event event = getEvent(agentId);
        AcdAgentOpStatDailyPO acdAgentOpStatDailyPO = acdAgentOpStatDailyHandler.poFromEvent(event);
        Assert.assertEquals(agentId, acdAgentOpStatDailyPO.getAgentId());
    }

    @Test
    public void poFromEventTest2() {
        String agentId = "agent4";
        Event event = getEvent(agentId);
        AcdAgentOpCheckinDailyPO acdAgentOpCheckinDailyPO = acdAgentOpCheckinDailyByShiftHandler.poFromEvent(event);
        Assert.assertEquals(agentId, acdAgentOpCheckinDailyPO.getAgentId());
    }

    private Event getEvent(String agentId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("duration", 1000);
        map.put("endType", 0);
        map.put("agentId", "[username:" + agentId + "@100645.cloud.justalk.com]");
        map.put("shift", "20220407");
        map.put("beginTimestamp", System.currentTimeMillis());
        map.put("endTimestamp", System.currentTimeMillis() + 10000);
        Event event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(100)
                .number(14)
                .params(map)
                .uuid(UUID.randomUUID().toString())
                .build();
        EventContext ec = new EventContext();
        ec.setRequestId(event.getUuid());
        ec.setEvent(event);
        ec.setFrom("host3");
        ec.setMagic("magic3");
        return event;
    }
}
