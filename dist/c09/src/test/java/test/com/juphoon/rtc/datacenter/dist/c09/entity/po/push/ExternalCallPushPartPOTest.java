package test.com.juphoon.rtc.datacenter.dist.c09.entity.po.push;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.push.ExternalCallPushPartPO;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
/**
 * <p>宁波外呼pushPOTest</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 用于测试宁波外呼pushPO
 */
public class ExternalCallPushPartPOTest {
    /**
     * 转换
     * (uniqueId = JSMS @ JSMS.Main0.Main01.Main, type = 26, status = 0, params = {
     *    "result" : 1,
     *    "requestAppParams" : {appId : 1111, taskNumber : "http://127.0.0.1:8000/?call=123"},
     *    "reason" : "leave",
     * }
     * , domainId=100645, appId=4)
     */
    @Test
    public void fromEventOk() {
        HashMap<String, Object> map = new HashMap(8);
        HashMap<Object, Object> test = new HashMap<>();
        map.put("result", 1);
        map.put("requestAppParams", test);
        map.put("reason", "leave");

        Event event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(26)
                .number(9)
                .timestamp(System.currentTimeMillis() + 100000000)
                .params(map)
                .uuid(UUID.randomUUID().toString())
                .build();
        ExternalCallPushPartPO po = new ExternalCallPushPartPO();
        po.setStatType((byte)1);
        try {
            po.fromEvent(event);
            Assert.assertNotNull(po.getUniqueKey());
            Assert.assertEquals(1, po.getResult().intValue());
            Assert.assertEquals(test, po.getRequestAppParams());
            Assert.assertEquals("leave", po.getReason());
            Assert.assertEquals(1, po.getSuccessCount().intValue());
            Assert.assertEquals(1, po.getTotalCount().intValue());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}