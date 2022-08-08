package test.com.juphoon.rtc.datacenter.dist.c09.entity.po;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.C09CommonPO;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * <p>宁波外呼需求公共po</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 测试宁波外呼需求公共po的转换
 */
public class C09CommonPOTest {

    @Test
    public void fromEvent() {
        Map<String, Object> map = new HashMap(8);
        map.put("a", 1);
        map.put("b", "null");
        map.put("c", "leave");


        Event event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(111)
                .number(111)
                .timestamp(System.currentTimeMillis() + 100000000)
                .params(map)
                .uuid(UUID.randomUUID().toString())
                .build();

        C09CommonPO po = new C09CommonPO();

        try {
            po.fromEvent(event);
            Assert.assertEquals(100645, po.getDomainId().intValue());
            Assert.assertEquals(0, po.getAppId().intValue());
            Assert.assertEquals(111, po.getType().intValue());
            Assert.assertEquals(111, po.getNumber().intValue());
            Assert.assertEquals(1, po.getCnt().intValue());
            Assert.assertEquals("leave", po.getParams().get("c"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}