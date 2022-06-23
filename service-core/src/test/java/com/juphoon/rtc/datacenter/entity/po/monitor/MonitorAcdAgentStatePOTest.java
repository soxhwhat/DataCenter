package com.juphoon.rtc.datacenter.entity.po.monitor;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

public class MonitorAcdAgentStatePOTest {


    /**
     * 转换
     * 状态数据上报:FlowStatusJson(
     *    uniqueId=[username:10637048@103291.cloud.justalk.com],
     *    type=12,
     *    status=0,
     *    params={
     *       "acdId" : "#CcOm@CcOm.Main2-0.Main",
     *       "agentId" : "[username:10637048@103291.cloud.justalk.com]",
     *       "agentStatus" : 1,
     *       "checkInTimestamp" : 1655254469663,
     *       "stateBeginTimestamp" : 1655254469663,
     *       "updateTimestamp" : 1655263195948
     *    },
     *    domainId=103291,
     *    appId=0
     * )
     */
    @Test
    public void fromStateOK() throws Exception {
        String agentId = "agentId";

        Map<String, Object> params = new HashMap<>();
        params.put("acdId", "acdId");
        params.put("agentId", agentId);
        params.put("agentStatus", 1);
        params.put("checkInTimestamp", 1L);
        params.put("stateBeginTimestamp", 1L);
        params.put("updateTimestamp", 1L);

        String json = new ObjectMapper().writeValueAsString(params);

        State state = State.builder()
                .uuid(agentId)
                .type(EventType.STAFF_BEAT.getType())
                .state(EventType.STAFF_BEAT.getNumber())
                .params(json).build();

        StateContext context = new StateContext(state);

        MonitorAcdAgentStatePO po = MonitorAcdAgentStatePO.fromState(context);

        Assert.assertNotNull(po);
        Assert.assertEquals(1L, po.getCheckInTimestamp().longValue());
        Assert.assertEquals("agentId", po.getAgentId());
        Assert.assertEquals(1, po.getState().intValue());
        Assert.assertEquals(1L, po.getCheckInTimestamp().longValue());
        Assert.assertEquals(1L, po.getStateBeginTimestamp().longValue());
        Assert.assertEquals(1L, po.getUpdateTimestamp().longValue());
    }

    /**
     * 无效JSON
     * @throws Exception
     */
    @Test(expected = JsonParseException.class)
    public void fromStateInvalidJson() throws Exception {
        String agentId = "agentId";

        State state = State.builder()
                .uuid(agentId)
                .type(EventType.STAFF_BEAT.getType())
                .state(EventType.STAFF_BEAT.getNumber())
                .params("test").build();

        StateContext context = new StateContext(state);

        MonitorAcdAgentStatePO.fromState(context);

        fail("不应该再这里");
    }

    /**
     * 值为空
     * @throws Exception
     */
    @Test(expected = AssertionError.class)
    public void fromStateInvalidFrom() throws Exception {
        String acdId = null;
        String agentId = null;

        Map<String, Object> params = new HashMap<>();
        params.put("acdId", acdId);
        params.put("agentId", agentId);
        params.put("agentStatus", 1);
        params.put("checkInTimestamp", 1L);
        params.put("stateBeginTimestamp", 1L);
        params.put("updateTimestamp", 1L);

        String json = new ObjectMapper().writeValueAsString(params);

        State state = State.builder()
                .uuid(agentId)
                .type(EventType.STAFF_BEAT.getType())
                .state(EventType.STAFF_BEAT.getNumber())
                .params(json).build();

        StateContext context = new StateContext(state);

        MonitorAcdAgentStatePO.fromState(context);

        fail("不应该再这里");
    }

    /**
     * 类型错误，应该是字符串
     * @throws Exception
     */
    @Test(expected = ClassCastException.class)
    public void fromStateInvalidFromType() throws Exception {
        Integer acdId = 1;
        String agentId = null;

        Map<String, Object> params = new HashMap<>();
        params.put("acdId", acdId);
        params.put("agentId", agentId);
        params.put("agentStatus", 1);
        params.put("checkInTimestamp", 1L);
        params.put("stateBeginTimestamp", 1L);
        params.put("updateTimestamp", 1L);

        String json = new ObjectMapper().writeValueAsString(params);

        State state = State.builder()
                .uuid(agentId)
                .type(EventType.STAFF_BEAT.getType())
                .state(EventType.STAFF_BEAT.getNumber())
                .params(json).build();

        StateContext context = new StateContext(state);

        MonitorAcdAgentStatePO.fromState(context);

        fail("不应该再这里");
    }

    /**
     * 转换，用于坐席签出
     * (
     *    domainId=100645,
     *    appId=4,
     *    uuid=a5f2be31-20a0-44bf-a062-9a3e1a19da00,
     *    type=100,
     *    number=14,
     *    timestamp= 1655710419029,
     *    updateTime=1655710421908,
     *    params={
     *       duration=0,
     *       agentId=[username:agent3@100645.cloud.justalk.com],
     *       beginTimestamp=1655710419029,
     *       endType=0,
     *       appId=4,
     *       endTimestamp=1655710419029,
     *       domainId=100645
     *    }
     * )
     */
    @Test
    public void fromEventOK() throws Exception {
        long checkOutTimestamp = 1L;
        String agentId = "agentId";

        Map<String, Object> params = new HashMap<>();
        params.put("duration", 0);
        params.put("agentId", agentId);
        params.put("beginTimestamp", 1L);
        params.put("endType", 0);
        params.put("appId", 4);
        params.put("domainId", 100645);
        params.put("endTimestamp", 1L);

        Event checkout = Event.builder()
                .domainId(100645)
                .appId(4)
                .type(EventType.AGENT_OP_EVENT_CHECK_OUT.getType())
                .number(EventType.AGENT_OP_EVENT_CHECK_OUT.getNumber())
                .timestamp(checkOutTimestamp)
                .params(params).build();

        EventContext ctx = new EventContext(checkout);

        MonitorAcdAgentStatePO po = MonitorAcdAgentStatePO.fromEvent(ctx, 0, 0);

        Assert.assertNotNull(po);
        Assert.assertEquals("agentId", po.getAgentId());
        Assert.assertEquals(0, po.getState().intValue());
        Assert.assertEquals(1L, po.getCheckOutTimestamp().longValue());
        Assert.assertEquals(1L, po.getStateBeginTimestamp().longValue());
        Assert.assertEquals(1L, po.getUpdateTimestamp().longValue());
    }

    @Test(expected = AssertionError.class)
    public void fromEventEmptyAgentId() throws Exception {
        long checkOutTimestamp = 1L;
        String agentId = "";

        Map<String, Object> params = new HashMap<>();
        params.put("duration", 0);
        params.put("agentId", agentId);
        params.put("beginTimestamp", 1L);
        params.put("endType", 0);
        params.put("appId", 4);
        params.put("domainId", 100645);
        params.put("endTimestamp", 1L);

        Event checkout = Event.builder()
                .domainId(100645)
                .appId(4)
                .type(EventType.AGENT_OP_EVENT_CHECK_OUT.getType())
                .number(EventType.AGENT_OP_EVENT_CHECK_OUT.getNumber())
                .timestamp(checkOutTimestamp)
                .params(params).build();

        EventContext ctx = new EventContext(checkout);

        MonitorAcdAgentStatePO.fromEvent(ctx, 0, 0);

        fail("不应该再这里");
    }

    @Test(expected = ClassCastException.class)
    public void fromEventInvalidAgentIdType() throws Exception {
        long checkOutTimestamp = 1L;
        int agentId = 0;

        Map<String, Object> params = new HashMap<>();
        params.put("duration", 0);
        params.put("agentId", agentId);
        params.put("beginTimestamp", 1L);
        params.put("endType", 0);
        params.put("appId", 4);
        params.put("domainId", 100645);
        params.put("endTimestamp", 1L);

        Event checkout = Event.builder()
                .domainId(100645)
                .appId(4)
                .type(EventType.AGENT_OP_EVENT_CHECK_OUT.getType())
                .number(EventType.AGENT_OP_EVENT_CHECK_OUT.getNumber())
                .timestamp(checkOutTimestamp)
                .params(params).build();

        EventContext ctx = new EventContext(checkout);

        MonitorAcdAgentStatePO.fromEvent(ctx, 0, 0);

        fail("不应该再这里");
    }
}