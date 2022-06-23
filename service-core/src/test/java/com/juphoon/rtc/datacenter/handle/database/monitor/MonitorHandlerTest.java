package com.juphoon.rtc.datacenter.handle.database.monitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.*;
import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorAcdAgentStatePO;
import com.juphoon.rtc.datacenter.mapper.MonitorAcdAgentStateMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.ACD_AGENT_STATE_CHECK_OUT;

/**
 * MonitorAcdAgentStateHandler Tester.
 *
 * @author ajian.zheng
 * @since <pre>Jun 22, 2022</pre>
 * @version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test-mysql")
public class MonitorHandlerTest {

    @Autowired
    private MonitorAcdAgentStateHandler monitorAcdAgentStateHandler;

    @Autowired
    private MonitorAcdAgentCheckoutHandler monitorAcdAgentCheckoutHandler;

    @Autowired
    private MonitorAcdAgentStateMapper agentStateMapper;

    private String agentId = "zhangsan." + System.currentTimeMillis();
    private Map<String, Object> params = new HashMap<>();

    @Before
    public void before() throws Exception {
        log.info("agent:{}", agentId);

        params.put("acdId", "#CcOm@CcOm.Main2-0.Main");
        params.put("agentId", agentId);
        params.put("agentStatus", 1);
        params.put("checkInTimestamp", 1L);
        params.put("stateBeginTimestamp", 1L);
        params.put("updateTimestamp", 1L);

        String json = new ObjectMapper().writeValueAsString(params);

        State state1 = State.builder()
                .uuid(agentId)
                .type(EventType.STAFF_BEAT.getType())
                .state(EventType.STAFF_BEAT.getNumber())
                .params(json).build();

        StateContext ctx1 = new StateContext(state1);

        log.info("ctx1:{}", ctx1);

        /// 首次处理，应该是insert
        monitorAcdAgentStateHandler.handle(ctx1);
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: handle(StateContext context)
     *
     * 状态数据上报:FlowStatusJson(
     *    uniqueId=[username:10637048@103291.cloud.justalk.com],
     *    type=12,
     *    status=0,
     *    params={
     *       "acdId" : "#CcOm@CcOm.Main2-0.Main",
     *       "agentId" : "[username:10637048@103291.cloud.justalk.com]",
     *       "agentStatus" : 1,
     *       "checkInTimestamp" : 1655254469663,
     *       "updateTimestamp" : 1655263195948
     *    },
     *    domainId=103291,
     *    appId=0
     * )
     */
    @Test
    public void testMonitorAcdAgentStateHandler() throws Exception {
        params.put("agentStatus", 2);
        params.put("stateBeginTimestamp", 2L);
        params.put("updateTimestamp", 2L);

        String json = new ObjectMapper().writeValueAsString(params);

        State state2 = State.builder()
                .uuid(agentId)
                .type(EventType.STAFF_BEAT.getType())
                .state(EventType.STAFF_BEAT.getNumber())
                .params(json).build();
        StateContext ctx2 = new StateContext(state2);

        /// 第二次处理，应该是update
        monitorAcdAgentStateHandler.handle(ctx2);

        MonitorAcdAgentStatePO po = agentStateMapper.selectByAgentId(agentId);
        Assert.assertNotNull(po);
        Assert.assertEquals(po.getState().intValue(), 2);
        Assert.assertEquals(po.getCheckInTimestamp().longValue(), 1L);
        Assert.assertEquals(po.getUpdateTimestamp().longValue(), 2L);
        Assert.assertEquals(po.getStateBeginTimestamp().longValue(), 2L);
    }

    /**
     * (
     *    domainId=100645,
     *    appId=4,
     *    uuid=a5f2be31-20a0-44bf-a062-9a3e1a19da00,
     *    type=100,
     *    number=14,
     *    timestamp=1655710419029,
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
    public void testMonitorAcdAgentCheckoutHandler() throws Exception {
        log.info("agent:{}", agentId);

        long checkOutTimestamp = 100L;

        Map<String, Object> params = new HashMap<>();
        params.put("duration", 0);
        params.put("agentId", agentId);
        params.put("beginTimestamp", 1L);
        params.put("endType", 0);
        params.put("appId", 4);
        params.put("domainId", 100645);
        params.put("endTimestamp", 100L);

        Event checkout = Event.builder()
                .domainId(100645)
                .appId(4)
                .type(EventType.AGENT_OP_EVENT_CHECK_OUT.getType())
                .number(EventType.AGENT_OP_EVENT_CHECK_OUT.getNumber())
                .timestamp(checkOutTimestamp)
                .params(params).build();

        EventContext ctx = new EventContext(checkout);

        /// 签出
        monitorAcdAgentCheckoutHandler.handle(ctx);

        MonitorAcdAgentStatePO po = agentStateMapper.selectByAgentId(agentId);
        Assert.assertNotNull("查询结果异常", po);
        Assert.assertEquals("查询结果异常", po.getCheckOutTimestamp().longValue(), checkOutTimestamp);
        Assert.assertEquals("查询结果异常", po.getUpdateTimestamp().longValue(), checkOutTimestamp);
        Assert.assertEquals("查询结果异常", po.getStateBeginTimestamp().longValue(), checkOutTimestamp);
        Assert.assertEquals("查询结果异常", po.getState(), ACD_AGENT_STATE_CHECK_OUT);
        Assert.assertEquals("查询结果异常", po.getSubState().intValue(), 0);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testMonitorAcdQueueCountHandler() throws Exception {

    }

    /**
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
     *
     * @throws Exception
     */
    @Test
    public void testMonitorRoomConcurrentHandler() throws Exception {

    }
}
