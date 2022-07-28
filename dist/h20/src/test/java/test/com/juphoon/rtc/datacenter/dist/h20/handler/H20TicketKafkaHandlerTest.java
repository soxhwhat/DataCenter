package test.com.juphoon.rtc.datacenter.dist.h20.handler;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.dist.h20.entity.KafkaTicketPo;
import com.juphoon.rtc.datacenter.dist.h20.handler.H20TicketKafkaHandler;
import com.juphoon.rtc.datacenter.servicecore.service.impl.RedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * <p>将排队机话单数据和sip媒体话单数据拼接后推送到kafka</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @date 8/1/22 11:26
 */
@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest({KafkaTicketPo.class})
public class H20TicketKafkaHandlerTest {

    @InjectMocks
    private H20TicketKafkaHandler h20TicketKafkaHandler;

    @Mock
    private RedisServiceImpl cacheService;

    @Test
    public void getDataTest() {
        Map<String, Object> map = new HashMap(3);
        map.put("callId", "20220407");
        Event event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(140)
                .number(1)
                .params(map)
                .timestamp(System.currentTimeMillis())
                .uuid(UUID.randomUUID().toString())
                .build();
        EventContext ec = new EventContext(event);
        ec.setRequestId(event.getUuid());
        ec.setFrom("host3");
        ec.setCreatedTimestamp(System.currentTimeMillis());
        KafkaTicketPo po = new KafkaTicketPo();
        po.setCallId("12345");
        po.setE55("e55");
        when(cacheService.hGet(any(), any())).thenReturn(po);

        KafkaTicketPo data = h20TicketKafkaHandler.getData(ec);
        Assert.assertEquals("12345", data.getCallId());
        Assert.assertEquals("e55", data.getE55());
    }

}
