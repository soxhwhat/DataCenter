package test.com.juphoon.rtc.datacenter.datacore.binlog.mapper;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FlashEventLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.juphoon.rtc.datacenter.datacore.DataCoreTestApplication;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static test.com.juphoon.rtc.datacenter.datacore.TestUtils.randomEventContext;

/**
 * AcdStatEventLogServiceSqliteImpl Tester.
 *
 * @author <Authors name>
 * @since <pre>May 12, 2022</pre>
 * @version 1.0
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
        logMapper.dropTable();
        logMapper.createTable();
    }

    @After
    public void after() {
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
    public void testRemove() {
        EventContext before = randomEventContext();
        EventBinLogPO po = EventBinLogPO.fromEventContext(before);

        log.info("before:{}", po.dump());
        logMapper.save(po);

        po = logMapper.findById(po.getId());
        log.info("get:{}", po.dump());

        logMapper.remove(po.getId());
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
        before();

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
