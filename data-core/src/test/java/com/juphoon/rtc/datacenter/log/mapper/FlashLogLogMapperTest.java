package com.juphoon.rtc.datacenter.log.mapper;

import com.juphoon.rtc.datacenter.TestApplication;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.binlog.entity.LogBinLogPO;
import com.juphoon.rtc.datacenter.binlog.mapper.flash.FlashLogLogMapper;
import com.juphoon.rtc.datacenter.binlog.mapper.reliable.ReliableEventLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AcdStatEventLogServiceSqliteImpl Tester.
 *
 * @author <Authors name>
 * @since <pre>May 12, 2022</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
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
    public void before() throws Exception {
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
}
