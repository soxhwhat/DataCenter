package test.com.juphoon.rtc.datacenter.servicecore.entity.po.monitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.datacore.api.State;
import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorConcurrentPO;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MonitorConcurrentPOTest {

    /**
     * 转换
     * 态数据上报:FlowStatusJson(uniqueId=JSMS@JSMS.Main0.Main01.Main, type=22, status=0, params={
     *    "appId" : 4,
     *    "concurrentNumber" : 1,
     *    "concurrentPerson" : 2,
     *    "domainId" : 100645,
     *    "from" : "JSMS@JSMS.Main0.Main01.Main",
     *    "resourceType" : 0,
     *    "updateTimeStamp" : "1655773334312"
     * }
     * , domainId=100645, appId=4)
     */
    @Test
    public void fromStateOK() throws Exception {
        String from = "from";

        Map<String, Object> params = new HashMap<>();
        params.put("appId", 0);
        params.put("concurrentNumber", 1);
        params.put("concurrentPerson", 2);
        params.put("domainId", 0);
        params.put("from", from);
        params.put("resourceType", 0);
        params.put("updateTimeStamp", "1");

        String json = new ObjectMapper().writeValueAsString(params);

        State state = State.builder()
                .uuid(from)
                .type(0)
                .state(0)
                .params(json).build();

        StateContext context = new StateContext(state);

        MonitorConcurrentPO po = MonitorConcurrentPO.fromState(context);

        Assert.assertNotNull(po);
        Assert.assertEquals(from, po.getFrom());
        Assert.assertEquals(1L, po.getTimestamp().longValue());
        Assert.assertEquals(1, po.getRoom().intValue());
        Assert.assertEquals(2, po.getActor().intValue());
    }
}