package test.com.juphoon.rtc.datacenter.dist.h20.handler;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.dist.h20.H20DataCenterApplication;
import com.juphoon.rtc.datacenter.dist.h20.entity.KafkaTicketPo;
import com.juphoon.rtc.datacenter.dist.h20.handler.H20SipTicketEventMongoHandler;
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
import java.util.*;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.INNER_TICKET_SIP;

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
public class H20SipTicketEventMongoHandlerTest {

    @Autowired
    private H20SipTicketEventMongoHandler h20SipTicketEventMongoHandler;

    @Autowired
    @Qualifier("recordMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Autowired
    protected RedisServiceImpl cacheService;

    @Test
    public void buildEventContextTest() {
        @NotNull Event event = getEventContext().getEvent();
        EventContext eventContext = h20SipTicketEventMongoHandler.buildEventContext(event);
        Assert.assertEquals(event.getParams().get("callId"), eventContext.getEvent().getParams().get("callId"));
    }

    @Test
    public void productEventTypeTest() {
        EventType eventType = h20SipTicketEventMongoHandler.productEventType();
        Assert.assertSame(INNER_TICKET_SIP, eventType);
    }

    @Test
    public void buildKafkaTicketPo() {
        KafkaTicketPo kafkaTicketPo = h20SipTicketEventMongoHandler.buildKafkaTicketPo(getEventContext().getEvent());
        Assert.assertEquals("|||1000,30,0,30|", kafkaTicketPo.getDetail());
    }

    @Test
    public void handleTest() {
        EventContext eventContext = getEventContext();
        boolean handle = h20SipTicketEventMongoHandler.handle(eventContext);
        Query query = new Query(new Criteria("params.callId").is(eventContext.getEvent().getParams().get("callId")));
        List<MongoEventPO> list = mongoTemplate.find(query, MongoEventPO.class, h20SipTicketEventMongoHandler.collectionName());
        KafkaTicketPo acd = (KafkaTicketPo) cacheService.hGet(INNER_TICKET_SIP.name(), (String) eventContext.getEvent().getParams().get("callId"));
        Assert.assertFalse(CollectionUtils.isEmpty(list));
        Assert.assertNotNull(acd);
        Assert.assertTrue(handle);
        Assert.assertEquals(acd.getCallId(), list.get(0).getParams().get("callId"));
    }

    private EventContext getEventContext() {
        Map<String, Object> detail = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> one = new HashMap<>();
        one.put("width", 1080);
        one.put("height", 960);
        one.put("time", 30);

        Map<String, Object> two = new HashMap<>();
        two.put("width", 480);
        two.put("height", 100);
        two.put("time", 30);
        list.add(one);
        list.add(two);

        detail.put("total", 60);
        detail.put("detail", list);
        Map<String, Object> map = new HashMap(3);
        map.put("audio", 1000);
        map.put("video", detail);
        map.put("callId", System.currentTimeMillis() + "");

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
