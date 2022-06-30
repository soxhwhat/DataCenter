package test.com.juphoon.rtc.datacenter.datacore.binlog.mapper;

import com.juphoon.rtc.datacenter.datacore.api.LogContext;
import com.juphoon.rtc.datacenter.datacore.binlog.entity.LogBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FlashLogLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.juphoon.rtc.datacenter.datacore.DataCoreTestApplication;

import java.util.ArrayList;
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
public class FlashLogLogMapperTest {
    public static LogContext randomContext() {
        List<String> logs = new ArrayList<>();
        logs.add("1");
        logs.add("2");
        logs.add("3");
        logs.add("4");
        logs.add("5");
        logs.add("6");
        logs.add("7");

        LogContext context = new LogContext(logs);
        String random = UUID.randomUUID().toString();

        context.setFrom(random);
        context.setRequestId(random);
        context.setProcessorId("test");

        return context;
    }


    @Autowired
    private FlashLogLogMapper logMapper;

    @Before
    public void before() {
        logMapper.dropTable();
        logMapper.createTable();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testInsert() {
        LogBinLogPO po = LogBinLogPO.fromContext(randomContext());

        int ret = logMapper.save(po);

        log.info("ret:{}", ret);
        log.info("id:{}", po.getId());
    }

    @Ignore
    @Test
    public void testInsertBatch() {
        int max = 100;

        List<LogContext> pos = new LinkedList<>();
        for (int i = 0; i < max; i++) {
            pos.add(randomContext());
        }

        List<LogBinLogPO> logs = pos.stream().map(LogBinLogPO::fromContext).collect(Collectors.toList());

        long start = System.currentTimeMillis();
        int lines = logMapper.saveList(logs);

        float cost = System.currentTimeMillis() - start;

        log.info("{} records effect {} cost:{}", max, lines, cost);
        log.info("{} per second", max / cost * 1000);

        logs.forEach(po -> log.info("{}", po.dump()));
    }

    @Test
    public void testRemove() {
        LogContext before = randomContext();
        LogBinLogPO po = LogBinLogPO.fromContext(before);

        log.info("before:{}", po.dump());
        logMapper.save(po);

        po = logMapper.findById(po.getId());
        log.info("get:{}", po.dump());

        logMapper.remove(po.getId());
    }

    @Test
    public void testFromPo() {
        LogContext before = randomContext();
        LogBinLogPO po = LogBinLogPO.fromContext(before);

        log.info("before:{}", po.dump());

        LogContext after = LogBinLogPO.toContext(po);

        Assert.assertEquals(before.getId(), after.getId());
    }

    @Test
    public void testFind() {
        before();

        LogBinLogPO p1 = LogBinLogPO.fromContext(randomContext());
        LogBinLogPO p2 = LogBinLogPO.fromContext(randomContext());
        LogBinLogPO p3 = LogBinLogPO.fromContext(randomContext());
        LogBinLogPO p4 = LogBinLogPO.fromContext(randomContext());
        LogBinLogPO p5 = LogBinLogPO.fromContext(randomContext());

        logMapper.save(p1);
        logMapper.save(p2);
        logMapper.save(p3);
        logMapper.save(p4);
        logMapper.save(p5);

        List<LogBinLogPO> ret = logMapper.find(1);
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
        LogBinLogPO po = LogBinLogPO.fromContext(randomContext());

        logMapper.save(po);

        po.setLastUpdateTimestamp(100L);

        logMapper.updateRetryCount(po);

        LogBinLogPO ret = logMapper.findById(po.getId());

        Assert.assertEquals(po.getLastUpdateTimestamp(), ret.getLastUpdateTimestamp());
        Assert.assertEquals(po.getRetryCount() + 1, ret.getRetryCount());
    }
}
