package test.com.juphoon.rtc.datacenter.dist.h20.handler;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.dist.h20.H20DataCenterApplication;
import com.juphoon.rtc.datacenter.dist.h20.entity.KafkaTicketPo;
import com.juphoon.rtc.datacenter.dist.h20.handler.H20AcdTicketEventMongoHandler;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity.MongoEventPO;
import com.juphoon.rtc.datacenter.servicecore.service.impl.RedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.INNER_TICKET_ACD;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @date 8/1/22 11:26
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = H20DataCenterApplication.class)
@ActiveProfiles("test")
public class H20AcdTicketEventMongoHandlerTest {

    @Autowired
    private H20AcdTicketEventMongoHandler h20AcdTicketEventMongoHandler;

    @Autowired
    @Qualifier("recordMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Autowired
    protected RedisServiceImpl cacheService;

    @Test
    public void buildEventContextTest() {
        @NotNull Event event = getEventContext().getEvent();
        EventContext eventContext = h20AcdTicketEventMongoHandler.buildEventContext(event);
        Assert.assertEquals(event.getParams().get("callId"), eventContext.getEvent().getParams().get("callId"));
    }

    @Test
    public void productEventTypeTest() {
        EventType eventType = h20AcdTicketEventMongoHandler.productEventType();
        Assert.assertSame(INNER_TICKET_ACD, eventType);
    }

    @Test
    public void handleTest() {
        EventContext eventContext = getEventContext();
        boolean handle = h20AcdTicketEventMongoHandler.handle(eventContext);
        Query query = new Query(new Criteria("params.callId").is(eventContext.getEvent().getParams().get("callId")));
        List<MongoEventPO> list = mongoTemplate.find(query, MongoEventPO.class, h20AcdTicketEventMongoHandler.collectionName());
        KafkaTicketPo acd = (KafkaTicketPo) cacheService.hGet(INNER_TICKET_ACD.name(), (String) eventContext.getEvent().getParams().get("callId"));
        Assert.assertFalse(CollectionUtils.isEmpty(list));
        Assert.assertNotNull(acd);
        Assert.assertTrue(handle);
        Assert.assertEquals(acd.getCallId(), list.get(0).getParams().get("callId"));
    }

    private EventContext getEventContext() {
        Map<String, Object> map = new HashMap(8);
        map.put("duration", 1000);
        map.put("caller", "sfds");
        map.put("callee", "agent123");
        map.put("agentId", "[username:agent3@100645.cloud.justalk.com]");
        map.put("callId", System.currentTimeMillis() + "");
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
        ec.setRequestId(event.getUuid());
        ec.setFrom("host3");
        ec.setCreatedTimestamp(System.currentTimeMillis());
        return ec;
    }

}
