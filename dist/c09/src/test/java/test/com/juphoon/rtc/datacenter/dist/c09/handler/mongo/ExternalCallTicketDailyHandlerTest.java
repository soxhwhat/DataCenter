package test.com.juphoon.rtc.datacenter.dist.c09.handler.mongo;


import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.ticket.ExternalCallTicketDailyPO;
import com.juphoon.rtc.datacenter.dist.c09.handle.ticket.ExternalCallTicketDailyHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.TICKER_COMPLETE;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_EVENT_EXTERNAL_TICKET_DAILY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

/**
 * <p>宁波客服话单HandlerTest</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 用于测试宁波客服话单Handler
 */
@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest({ExternalCallTicketDailyPO.class})
public class ExternalCallTicketDailyHandlerTest {
    @InjectMocks
    private ExternalCallTicketDailyHandler externalCallTicketDailyHandler;

    @Mock
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    EventContext ec;
    Event event;

    @Before
    public void initEc() {
        Map<String, Object> map = new HashMap<>(8);
        HashMap<Object, Object> test = new HashMap<>();
        map.put("talkTime", 1000);
        map.put("waitTime", 1000);


        event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(26)
                .number(0)
                .timestamp(System.currentTimeMillis() + 100000000)
                .params(map)
                .uuid(UUID.randomUUID().toString())
                .build();

        ec = new EventContext(event);
    }

    @Test
    public void collectionNameTest() {
        String name = externalCallTicketDailyHandler.collectionName().getName();
        Assert.assertSame(COLLECTION_EVENT_EXTERNAL_TICKET_DAILY.getName(), name);
    }

    @Test
    public void productEventTypeTest() {
        List<EventType> eventTypes = externalCallTicketDailyHandler.careEvents();
        EventType eventType = eventTypes.get(0);
        Assert.assertSame(TICKER_COMPLETE, eventType);
    }

    @Test
    public void handleOk() {
        ExternalCallTicketDailyPO po = new ExternalCallTicketDailyPO();
        ExternalCallTicketDailyHandler mock = Mockito.mock(ExternalCallTicketDailyHandler.class);

        PowerMockito.when(mock.poFromEvent(event)).thenReturn(po);
        externalCallTicketDailyHandler.handle(ec);
        Mockito.verify(mongoTemplate, times(1)).upsert(any(), any(), (String) any());

    }

    @Test
    public void getDataTest() {
        ExternalCallTicketDailyPO po = externalCallTicketDailyHandler.poFromEvent(event);
        Assert.assertNotNull(po);
        Assert.assertEquals(1, po.getSuccessCount().intValue());
        Assert.assertEquals(1, po.getTotalCount().intValue());
        Assert.assertEquals((byte) 4, externalCallTicketDailyHandler.statType().getStatType());
    }
}