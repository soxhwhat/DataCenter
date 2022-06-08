package com.juphoon.rtc.datacenter.log.mapper;

import com.juphoon.rtc.datacenter.TestApplication;
import com.juphoon.rtc.datacenter.api.State;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.binlog.entity.StateBinLogPO;
import com.juphoon.rtc.datacenter.binlog.mapper.flash.FlashStateLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
@SpringBootTest(classes = TestApplication.class)
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
    public void before() throws Exception {
        logMapper.dropTable();
        logMapper.createTable();
    }

    @After
    public void after() throws Exception {
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
    public void testRemove() {
        StateContext before = randomContext();
        StateBinLogPO po = StateBinLogPO.fromContext(before);

        log.info("before:{}", po.dump());
        logMapper.save(po);

        po = logMapper.findById(po.getId());
        log.info("get:{}", po.dump());

        logMapper.remove(po.getId());
    }

    @Test
    public void testFromPo() {
        StateContext before = randomContext();
        StateBinLogPO po = StateBinLogPO.fromContext(before);

        log.info("before:{}", po.dump());

        StateContext after = StateBinLogPO.toContext(po);

        Assert.assertEquals(before.getId(), after.getId());
    }
}
