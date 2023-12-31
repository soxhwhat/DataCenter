package test.com.juphoon.rtc.datacenter.datacore.binlog.mapper;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FlashEventLogMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.juphoon.rtc.datacenter.datacore.DataCoreTestApplication;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.juphoon.rtc.datacenter.datacore.utils.TestUtils.randomEventContext;

/**
 * AcdStatEventLogServiceSqliteImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>May 12, 2022</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCoreTestApplication.class)
@ActiveProfiles("test")
@Slf4j
public class FlashEventLogMapperTest {
    @Autowired
    private FlashEventLogMapper logMapper;

    @Before
    public void before() {
        try {
            logMapper.dropTable();
            logMapper.createTable();
        } catch (SQLException e) {
            Assert.fail("创建表失败");
        }
    }

    @SneakyThrows
    @After
    public void after() {
        logMapper.dropTable();
    }

    @Test
    public void testInsert() {
        EventBinLogPO po = EventBinLogPO.fromEventContext(randomEventContext());

        int ret = logMapper.save(po);

        log.info("ret:{}", ret);
        log.info("id:{}", po.getId());
    }

    @Ignore
    @Test
    public void testInsertEventBatch() {
        int max = 100;

        List<EventContext> eventContexts = new LinkedList<>();
        for (int i = 0; i < max; i++) {
            eventContexts.add(randomEventContext());
        }

        List<EventBinLogPO> events = eventContexts.stream().map(EventBinLogPO::fromEventContext).collect(Collectors.toList());

        long start = System.currentTimeMillis();
        int lines = logMapper.saveList(events);

        float cost = System.currentTimeMillis() - start;

        log.info("{} records effect {} cost:{}", max, lines, cost);
        log.info("{} per second", max / cost * 1000);

        events.forEach(eventBinLogPO -> log.info("{}", eventBinLogPO.dump()));
    }

    @Test
    public void testCountInsertBatch() {
        int max = 500;

        List<EventContext> eventContexts = new LinkedList<>();
        for (int i = 0; i < max; i++) {
            eventContexts.add(randomEventContext());
        }

        List<EventBinLogPO> events = eventContexts.stream().map(EventBinLogPO::fromEventContext).collect(Collectors.toList());

        int lines = logMapper.saveList(events);
        Assert.assertEquals(max, lines);

        List<EventBinLogPO> eventBinLogPOList = logMapper.find(max);
        Assert.assertEquals(max, eventBinLogPOList.size());


        int min = 10;

        eventContexts = new LinkedList<>();
        for (int i = 0; i < min; i++) {
            eventContexts.add(randomEventContext());
        }

        events = eventContexts.stream().map(EventBinLogPO::fromEventContext).collect(Collectors.toList());

        lines = logMapper.saveList(events);
        Assert.assertEquals(min, lines);

        eventBinLogPOList = logMapper.find(min);
        Assert.assertEquals(min, eventBinLogPOList.size());
    }

    @Test
    public void testRemove() {
        EventContext before = randomEventContext();
        EventBinLogPO po = EventBinLogPO.fromEventContext(before);

        log.info("before:{}", po.dump());
        logMapper.save(po);

        po = logMapper.findById(po.getId());
        log.info("get:{}", po.dump());

        logMapper.remove(po.getId());

        po = logMapper.findById(po.getId());
        Assert.assertNull(po);
    }

    @Test
    public void testBatchRemove() {
        EventBinLogPO p1 = EventBinLogPO.fromEventContext(randomEventContext());
        EventBinLogPO p2 = EventBinLogPO.fromEventContext(randomEventContext());
        EventBinLogPO p3 = EventBinLogPO.fromEventContext(randomEventContext());
        EventBinLogPO p4 = EventBinLogPO.fromEventContext(randomEventContext());
        EventBinLogPO p5 = EventBinLogPO.fromEventContext(randomEventContext());

        logMapper.save(p1);
        logMapper.save(p2);
        logMapper.save(p3);
        logMapper.save(p4);
        logMapper.save(p5);

        List<Long> ids = new LinkedList<>();
        ids.add(p1.getId());
        ids.add(p2.getId());
        ids.add(p3.getId());
        ids.add(p4.getId());
        ids.add(p5.getId());

        List<EventBinLogPO> ret = logMapper.find(10);
        Assert.assertEquals(5, ret.size());

        logMapper.remove(ids);

        ret = logMapper.find(10);
        Assert.assertTrue(ret.isEmpty());
    }

    @Test
    public void testFromPo() {
        EventContext before = randomEventContext();
        EventBinLogPO po = EventBinLogPO.fromEventContext(before);

        log.info("before:{}", po.dump());

        EventContext after = EventBinLogPO.toEventContext(po);

        Assert.assertEquals(before.getId(), after.getId());
    }

    @Test
    public void testFind() {
        EventBinLogPO p1 = EventBinLogPO.fromEventContext(randomEventContext());
        EventBinLogPO p2 = EventBinLogPO.fromEventContext(randomEventContext());
        EventBinLogPO p3 = EventBinLogPO.fromEventContext(randomEventContext());
        EventBinLogPO p4 = EventBinLogPO.fromEventContext(randomEventContext());
        EventBinLogPO p5 = EventBinLogPO.fromEventContext(randomEventContext());

        logMapper.save(p1);
        logMapper.save(p2);
        logMapper.save(p3);
        logMapper.save(p4);
        logMapper.save(p5);

        List<EventBinLogPO> ret = logMapper.find(1);
        Assert.assertEquals(1, ret.size());
        Assert.assertEquals(p1.getId(), ret.get(0).getId());

        ret = logMapper.find(2);
        Assert.assertEquals(2, ret.size());
        Assert.assertEquals(p2.getId(), ret.get(1).getId());

        ret = logMapper.find(5);
        Assert.assertEquals(5, ret.size());
        Assert.assertEquals(p5.getId(), ret.get(4).getId());

        ret = logMapper.find(10);
        Assert.assertEquals(5, ret.size());
        Assert.assertEquals(p5.getId(), ret.get(4).getId());
    }

    @Test
    public void testFindById() {
        before();

        EventBinLogPO p1 = EventBinLogPO.fromEventContext(randomEventContext());
        EventBinLogPO p2 = EventBinLogPO.fromEventContext(randomEventContext());
        EventBinLogPO p3 = EventBinLogPO.fromEventContext(randomEventContext());

        logMapper.save(p1);
        logMapper.save(p2);
        logMapper.save(p3);

        EventBinLogPO ret1 = logMapper.findById(p1.getId());
        Assert.assertNotNull(ret1.getUuid());
        Assert.assertEquals(p1.getUuid(), ret1.getUuid());

        EventBinLogPO ret2 = logMapper.findById(p2.getId());
        Assert.assertNotNull(ret2.getUuid());
        Assert.assertEquals(p2.getUuid(), ret2.getUuid());

        EventBinLogPO ret3 = logMapper.findById(p3.getId());
        Assert.assertNotNull(ret3.getUuid());
        Assert.assertEquals(p3.getUuid(), ret3.getUuid());

    }

    @Test
    public void testUpdateRetryCount() {
        EventBinLogPO po = EventBinLogPO.fromEventContext(randomEventContext());

        logMapper.save(po);

        po.setLastUpdateTimestamp(100L);

        logMapper.updateRetryCount(po);

        EventBinLogPO ret = logMapper.findById(po.getId());

        Assert.assertEquals(po.getLastUpdateTimestamp(), ret.getLastUpdateTimestamp());
        Assert.assertEquals(po.getRetryCount() + 1, ret.getRetryCount());
    }
}
