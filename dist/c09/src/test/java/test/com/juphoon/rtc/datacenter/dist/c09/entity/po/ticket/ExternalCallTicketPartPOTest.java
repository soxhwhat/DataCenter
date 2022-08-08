package test.com.juphoon.rtc.datacenter.dist.c09.entity.po.ticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.push.ExternalCallPushPartPO;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.ticket.ExternalCallTicketPartPO;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;

/**
 * <p>宁波客服话单POTest</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 用于测试宁波客服话单PO的转换
 */
public class ExternalCallTicketPartPOTest {
    /**
     * 转换
     * (uniqueId = JSMS @ JSMS.Main0.Main01.Main, type = 11, status = 1, params = {
     *    "waitTime" : 1,
     *    ”talkTime“ : 1,
     * }
     * , domainId=100645, appId=4)
     */
    @Test
    public void fromEventOk() {
        HashMap<String, Object> map = new HashMap(8);
        map.put("talkTime",1000L);
        map.put("waitTime",1000L);

        Event event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(11)
                .number(1)
                .timestamp(System.currentTimeMillis() + 100000000)
                .params(map)
                .uuid(UUID.randomUUID().toString())
                .build();
        ExternalCallTicketPartPO po = new ExternalCallTicketPartPO();
        po.setStatType((byte)1);
        try {
            po.fromEvent(event);
            Assert.assertNotNull(po.getUniqueKey());
            Assert.assertEquals(1000, po.getTalkTime().longValue());
            Assert.assertEquals(1000, po.getWaitTime().longValue());
            Assert.assertEquals(1, po.getSuccessCount().intValue());
            Assert.assertEquals(1, po.getTotalCount().intValue());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}