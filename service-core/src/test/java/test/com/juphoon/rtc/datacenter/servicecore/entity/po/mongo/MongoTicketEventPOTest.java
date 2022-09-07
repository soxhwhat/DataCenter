package test.com.juphoon.rtc.datacenter.servicecore.entity.po.mongo;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity.MongoTicketEventPO;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/7/30 18:40
 */
public class MongoTicketEventPOTest {

    /**
     * domainId
     * appId
     * beginTimestamp
     * endTimestamp
     * bizId
     * callId
     * queue
     * guestId
     * agentId
     * endType
     * recordMode
     * waitTime
     * ringingTime
     * talkTime
     * timeoutCount
     * transferCount
     * inviteCount
     * aiId
     * source
     * gmtCreate
     * acdCallType
     */
    @Test
    public void fromEventOk() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("traceId", "traceId");
        map.put("domainId", 100645);
        map.put("appId", 4);
        map.put("callId", "1ddac265-d4cd-49a8-86f0-730dcd2ebd34");
        map.put("talkTime", 5000);
        map.put("beginTimestamp", System.currentTimeMillis());
        map.put("endTimestamp", System.currentTimeMillis());
        map.put("source", "acd");

        Event event = Event.builder()
                .domainId(100645)
                .appId(4)
                .type(11)
                .number(1)
                .timestamp(System.currentTimeMillis() + 100000000)
                .params(map)
                .uuid(UUID.randomUUID().toString())
                .build();
        EventContext ec = new EventContext(event);

        MongoTicketEventPO ticket = new MongoTicketEventPO();
        ticket.fromEvent(ec.getEvent());

        Assert.assertEquals(11, ticket.getType().longValue());
        Assert.assertEquals("1ddac265-d4cd-49a8-86f0-730dcd2ebd34", ticket.getParams().get("callId"));
        Assert.assertEquals(1, ticket.getNumber().intValue());
        Assert.assertEquals(5000, ticket.getParams().get("talkTime"));
    }
}
