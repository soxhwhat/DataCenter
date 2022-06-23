package com.juphoon.rtc.datacenter.entity.po.monitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.State;
import com.juphoon.rtc.datacenter.api.StateContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MonitorAcdQueueCountPOTest {

    /**
     * 转换
     * (
     *  uniqueId=7000,
     *  type=13,
     *  status=0,
     *  params={
     *    "from" : "#CcAcd@CcAcd.Main2-0.Main",
     *    "queue" : "7000",
     *    "timestamp" : 1655263216229,
     *    "waitCount" : 0
     *  }
     */
    @Test
    public void fromStateOK() throws Exception {
        String queue = "queue";
        String from = "from";

        Map<String, Object> params = new HashMap<>();
        params.put("from", "from");
        params.put("queue", queue);
        params.put("timestamp", 1L);
        params.put("waitCount", 1);

        String json = new ObjectMapper().writeValueAsString(params);

        State state = State.builder()
                .uuid(queue)
                .type(0)
                .state(0)
                .params(json).build();

        StateContext context = new StateContext(state);

        MonitorAcdQueueCountPO po = MonitorAcdQueueCountPO.fromState(context);

        Assert.assertNotNull(po);
        Assert.assertEquals(queue, po.getQueue());
        Assert.assertEquals(from, po.getFrom());
        Assert.assertEquals(1L, po.getTimestamp().longValue());
        Assert.assertEquals(1, po.getCount().intValue());
    }
}