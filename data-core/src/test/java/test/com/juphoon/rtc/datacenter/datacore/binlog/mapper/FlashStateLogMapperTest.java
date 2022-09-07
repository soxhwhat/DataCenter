package test.com.juphoon.rtc.datacenter.datacore.binlog.mapper;

import com.juphoon.rtc.datacenter.datacore.api.State;
import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.datacore.binlog.entity.StateBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FlashStateLogMapper;
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
import java.util.UUID;
import java.util.stream.Collectors;

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
public class FlashStateLogMapperTest {


    public static StateContext randomContext() {
        StateContext context = new StateContext(randomState());
        String random = UUID.randomUUID().toString();

        context.setFrom(random);
        context.setRequestId(random);
        context.setProcessorId("test");

        return context;
    }

    public static State randomState() {
        return State.builder().domainId(100645).appId(0).type(1).state(2).uuid(UUID.randomUUID().toString())
                .params("hello world").build();
    }

    @Autowired
    private FlashStateLogMapper logMapper;

    @Before
    public void before() {
        logMapper.dropTable();
        try {
            logMapper.createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @After
    public void after() {
        logMapper.dropTable();
    }

    @Test
    public void testInsert() {
        StateBinLogPO po = StateBinLogPO.fromContext(randomContext());

        int ret = logMapper.save(po);

        log.info("ret:{}", ret);
        log.info("id:{}", po.getId());
    }

    @Ignore
    @Test
    public void testInsertBatch() {
        int max = 100;

        List<StateContext> list = new LinkedList<>();
        for (int i = 0; i < max; i++) {
            list.add(randomContext());
        }

        List<StateBinLogPO> pos = list.stream().map(StateBinLogPO::fromContext).collect(Collectors.toList());

        long start = System.currentTimeMillis();
        int lines = logMapper.saveList(pos);

        float cost = System.currentTimeMillis() - start;

        log.info("{} records effect {} cost:{}", max, lines, cost);
        log.info("{} per second", max / cost * 1000);

        pos.forEach(po -> log.info("{}", po.dump()));
    }

    @Test
    public void testLessCountInsertBatch() {
        int max = 500;

        List<StateContext> list = new LinkedList<>();
        for (int i = 0; i < max; i++) {
            list.add(randomContext());
        }

        List<StateBinLogPO> pos = list.stream().map(StateBinLogPO::fromContext).collect(Collectors.toList());

        int lines = logMapper.saveList(pos);
        Assert.assertEquals(max, lines);

        List<StateBinLogPO> eventBinLogPOList = logMapper.find(max);
        Assert.assertEquals(max, eventBinLogPOList.size());


        int min = 10;

        list = new LinkedList<>();
        for (int i = 0; i < min; i++) {
            list.add(randomContext());
        }

        pos = list.stream().map(StateBinLogPO::fromContext).collect(Collectors.toList());

        lines = logMapper.saveList(pos);
        Assert.assertEquals(min, lines);

        eventBinLogPOList = logMapper.find(min);
        Assert.assertEquals(min, eventBinLogPOList.size());
    }

    @Test
    public void testRemove() {
        StateContext before = randomContext();
        StateBinLogPO po = StateBinLogPO.fromContext(before);

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
        StateBinLogPO p1 = StateBinLogPO.fromContext(randomContext());
        StateBinLogPO p2 = StateBinLogPO.fromContext(randomContext());
        StateBinLogPO p3 = StateBinLogPO.fromContext(randomContext());
        StateBinLogPO p4 = StateBinLogPO.fromContext(randomContext());
        StateBinLogPO p5 = StateBinLogPO.fromContext(randomContext());

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

        List<StateBinLogPO> ret = logMapper.find(10);
        Assert.assertEquals(5, ret.size());

        logMapper.remove(ids);

        ret = logMapper.find(10);
        Assert.assertTrue(ret.isEmpty());
    }

    @Test
    public void testFromPo() {
        StateContext before = randomContext();
        StateBinLogPO po = StateBinLogPO.fromContext(before);

        log.info("before:{}", po.dump());

        StateContext after = StateBinLogPO.toContext(po);

        Assert.assertEquals(before.getId(), after.getId());
    }

    @Test
    public void testFind() {
        before();

        StateBinLogPO p1 = StateBinLogPO.fromContext(randomContext());
        StateBinLogPO p2 = StateBinLogPO.fromContext(randomContext());
        StateBinLogPO p3 = StateBinLogPO.fromContext(randomContext());
        StateBinLogPO p4 = StateBinLogPO.fromContext(randomContext());
        StateBinLogPO p5 = StateBinLogPO.fromContext(randomContext());

        logMapper.save(p1);
        logMapper.save(p2);
        logMapper.save(p3);
        logMapper.save(p4);
        logMapper.save(p5);

        List<StateBinLogPO> ret = logMapper.find(1);
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

        StateBinLogPO p1 = StateBinLogPO.fromContext(randomContext());
        StateBinLogPO p2 = StateBinLogPO.fromContext(randomContext());
        StateBinLogPO p3 = StateBinLogPO.fromContext(randomContext());

        logMapper.save(p1);
        logMapper.save(p2);
        logMapper.save(p3);

        StateBinLogPO ret1 = logMapper.findById(p1.getId());
        Assert.assertNotNull(ret1.getUuid());
        Assert.assertEquals(p1.getUuid(), ret1.getUuid());

        StateBinLogPO ret2 = logMapper.findById(p2.getId());
        Assert.assertNotNull(ret2.getUuid());
        Assert.assertEquals(p2.getUuid(), ret2.getUuid());

        StateBinLogPO ret3 = logMapper.findById(p3.getId());
        Assert.assertNotNull(ret3.getUuid());
        Assert.assertEquals(p3.getUuid(), ret3.getUuid());

    }

    @Test
    public void testUpdateRetryCount() {
        StateBinLogPO po = StateBinLogPO.fromContext(randomContext());

        logMapper.save(po);

        po.setLastUpdateTimestamp(100L);

        logMapper.updateRetryCount(po);

        StateBinLogPO ret = logMapper.findById(po.getId());

        Assert.assertEquals(po.getLastUpdateTimestamp(), ret.getLastUpdateTimestamp());
        Assert.assertEquals(po.getRetryCount() + 1, ret.getRetryCount());
    }
}
