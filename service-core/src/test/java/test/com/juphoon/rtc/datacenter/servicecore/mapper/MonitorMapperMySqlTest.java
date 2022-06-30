package test.com.juphoon.rtc.datacenter.servicecore.mapper;

import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorAcdAgentStatePO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorAcdQueueCountPO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorRoomConcurrentPO;
import com.juphoon.rtc.datacenter.servicecore.mapper.MonitorAcdAgentStateMapper;
import com.juphoon.rtc.datacenter.servicecore.mapper.MonitorAcdQueueCountMapper;
import com.juphoon.rtc.datacenter.servicecore.mapper.MonitorRoomConcurrentMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.juphoon.rtc.datacenter.servicecore.ServiceCoreTestApplication;

/**
 * MonitorAcdAgentCheckoutHandler Tester.
 *
 * @author ajian.zheng
 * @since <pre>Jun 21, 2022</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceCoreTestApplication.class)
@ActiveProfiles("test-mysql")
public class MonitorMapperMySqlTest {

    @Autowired
    private MonitorAcdAgentStateMapper agentStateMapper;

    @Autowired
    private MonitorAcdQueueCountMapper queueCountMapper;

    @Autowired
    private MonitorRoomConcurrentMapper roomConcurrentMapper;

    private String agentId = "" + System.currentTimeMillis();


    @Before
    public void before() throws Exception {

    }

    @After
    public void after() throws Exception {
    }

    MonitorAcdAgentStatePO initPO(String agentId) {
        MonitorAcdAgentStatePO po = new MonitorAcdAgentStatePO();

        po.setDomainId(1);
        po.setAppId(2);
        po.setAgentId(agentId);
        po.setCheckInTimestamp(4L);
        po.setUpdateTimestamp(5L);
        po.setCheckOutTimestamp(6L);
        po.setStateBeginTimestamp(7L);
        po.setState(8);
        po.setSubState(9);

        return po;
    }

    @Test
    public void testMonitorRoomConcurrentMapper() throws Exception {
        long now = System.currentTimeMillis();
        MonitorRoomConcurrentPO ret = roomConcurrentMapper.selectConcurrent(now);
        Assert.assertNull("结果异常", ret);

        MonitorRoomConcurrentPO p1 = new MonitorRoomConcurrentPO();
        MonitorRoomConcurrentPO p2 = new MonitorRoomConcurrentPO();
        MonitorRoomConcurrentPO p3 = new MonitorRoomConcurrentPO();

        p1.setFrom("" + now);
        p1.setTimestamp(now);
        p1.setDomainId(1);
        p1.setAppId(2);
        p1.setActor(1);
        p1.setRoom(1);

        p2.setFrom("2." + now);
        p2.setTimestamp(now);
        p2.setDomainId(1);
        p2.setAppId(2);
        p2.setActor(1);
        p2.setRoom(1);

        /// 过期，不应该算进去
        p3.setFrom("3." + now);
        p3.setTimestamp(now - 1);
        p3.setDomainId(2);
        p3.setAppId(2);
        p3.setActor(10);
        p3.setRoom(10);

        roomConcurrentMapper.insert(p1);
        roomConcurrentMapper.insert(p2);
        roomConcurrentMapper.insert(p3);

        ret = roomConcurrentMapper.selectConcurrent(now - 1);
        Assert.assertNotNull("结果异常", ret);
        Assert.assertEquals("结果异常", ret.getRoom().intValue(), 2);
        Assert.assertEquals("结果异常", ret.getActor().intValue(), 2);

        /// p1 更新room=10, 总数10+1=11
        p1.setRoom(10);
        roomConcurrentMapper.updateByFromAndDomainIdAndAppId(p1);

        ret = roomConcurrentMapper.selectConcurrent(now - 1);
        Assert.assertNotNull("结果异常", ret);
        Assert.assertEquals("结果异常", ret.getRoom().intValue(), 11);
        Assert.assertEquals("结果异常", ret.getActor().intValue(), 2);
    }

    /**
     * update
     */
    @Test
    public void testMonitorAcdQueueCountMapper() throws Exception {
        MonitorAcdQueueCountPO p1 = new MonitorAcdQueueCountPO();
        MonitorAcdQueueCountPO p2 = new MonitorAcdQueueCountPO();
        MonitorAcdQueueCountPO p3 = new MonitorAcdQueueCountPO();

        String queue = "" + System.currentTimeMillis();
        String queue2 = "2." + System.currentTimeMillis();

        p1.setQueue(queue);
        p1.setFrom("1");
        p1.setTimestamp(3L);
        p1.setCount(1);
        p1.setType(5);
        p1.setNumber(6);
        p1.setDomainId(7);
        p1.setAppId(8);

        p2.setQueue(queue);
        p2.setFrom("2");
        p2.setTimestamp(3L);
        p2.setCount(1);
        p2.setType(5);
        p2.setNumber(6);
        p2.setDomainId(7);
        p2.setAppId(8);

        p3.setQueue(queue2);
        p3.setFrom("1");
        p3.setTimestamp(3L);
        p3.setCount(4);
        p3.setType(5);
        p3.setNumber(6);
        p3.setDomainId(7);
        p3.setAppId(8);

        Integer ret = queueCountMapper.selectSumCountByQueue(queue);
        Assert.assertNull("结果异常", ret);

        queueCountMapper.insert(p1);
        queueCountMapper.insert(p2);
        queueCountMapper.insert(p3);

        ret = queueCountMapper.selectSumCountByQueue(queue);
        Assert.assertEquals("结果异常", 2, ret.intValue());

        p1.setCount(2);
        p1.setTimestamp(9L);
        queueCountMapper.updateByQueueAndFrom(p1);

        ret = queueCountMapper.selectSumCountByQueue(queue);
        Assert.assertEquals("结果异常", 3, ret.intValue());
    }

    /**
     * update
     */
    @Test
    public void testMonitorAcdAgentStateMapper() throws Exception {

        MonitorAcdAgentStatePO po = initPO(agentId);

        MonitorAcdAgentStatePO saved = agentStateMapper.selectByAgentId(agentId);
        Assert.assertNull("数据未清空", saved);

        int ret = agentStateMapper.insert(po);
        Assert.assertEquals("数据未清空", ret, 1);

        saved = agentStateMapper.selectByAgentId(agentId);
        Assert.assertNotNull("插入失败", saved);
        Assert.assertEquals("查询结果异常", saved.getDomainId(), po.getDomainId());
        Assert.assertEquals("查询结果异常", saved.getAppId(), po.getAppId());
        Assert.assertEquals("查询结果异常", saved.getAgentId(), po.getAgentId());
        Assert.assertEquals("查询结果异常", saved.getCheckInTimestamp(), po.getCheckInTimestamp());
        Assert.assertEquals("查询结果异常", saved.getUpdateTimestamp(), po.getUpdateTimestamp());
        Assert.assertEquals("查询结果异常", saved.getStateBeginTimestamp(), po.getStateBeginTimestamp());
        Assert.assertEquals("查询结果异常", saved.getState(), po.getState());
        Assert.assertEquals("查询结果异常", saved.getSubState(), po.getSubState());

        po.setState(0);
        po.setSubState(0);
        ret = agentStateMapper.updateStateByAgentId(po);
        Assert.assertEquals("数据更新异常", ret, 1);

        saved = agentStateMapper.selectByAgentId(agentId);
        Assert.assertNotNull("查询结果异常", saved);
        Assert.assertEquals("查询结果异常", saved.getState(), po.getState());
        Assert.assertEquals("查询结果异常", saved.getSubState(), po.getSubState());

        po.setCheckOutTimestamp(1L);
        po.setState(1);
        po.setSubState(1);
        ret = agentStateMapper.updateCheckOutByAgentId(po);
        Assert.assertEquals("数据更新异常", ret, 1);

        saved = agentStateMapper.selectByAgentId(agentId);
        Assert.assertNotNull("查询结果异常", saved);
        Assert.assertEquals("查询结果异常", po.getCheckOutTimestamp(), saved.getCheckOutTimestamp());
        Assert.assertEquals("查询结果异常", po.getCheckOutTimestamp(), saved.getUpdateTimestamp());
        Assert.assertEquals("查询结果异常", po.getCheckOutTimestamp(), saved.getStateBeginTimestamp());
        Assert.assertEquals("查询结果异常", po.getState(), saved.getState());
        Assert.assertEquals("查询结果异常", po.getSubState(), saved.getSubState());
    }


} 
