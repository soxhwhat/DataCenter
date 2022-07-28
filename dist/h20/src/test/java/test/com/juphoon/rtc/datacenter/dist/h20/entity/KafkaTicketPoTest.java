package test.com.juphoon.rtc.datacenter.dist.h20.entity;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.dist.h20.entity.KafkaTicketPo;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wenjun.yuan@juphoon.com
 * @date 8/1/22 11:26
 */
public class KafkaTicketPoTest {

    @Test
    public void fromEventOk() {
        Map<String, Object> map = new HashMap(8);
        map.put("duration", 1000);
        map.put("caller", "sfds");
        map.put("callee", "agent123");
        map.put("agentId", "[username:agent3@100645.cloud.justalk.com]");
        map.put("callId", "20220407");
        map.put("groupId", "team11234");
        map.put("e55", "1");
        map.put("beginTimestamp", 1647935486800L);
        map.put("endTimestamp", 1649266984459L);

        Event event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(11)
                .number(2)
                .timestamp(System.currentTimeMillis())
                .params(map)
                .uuid(UUID.randomUUID().toString())
                .build();
        EventContext ec = new EventContext(event);

        KafkaTicketPo kafkaTicketPo = new KafkaTicketPo();
        kafkaTicketPo.fromEvent(ec.getEvent());

        Assert.assertEquals("sfds", kafkaTicketPo.getFrom());
        Assert.assertEquals("20220407", kafkaTicketPo.getCallId());
        Assert.assertEquals("1", kafkaTicketPo.getDuration());
        Assert.assertEquals("team11234", kafkaTicketPo.getGroupId());
    }

}

