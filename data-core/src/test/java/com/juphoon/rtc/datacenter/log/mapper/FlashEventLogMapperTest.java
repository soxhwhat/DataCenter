package com.juphoon.rtc.datacenter.log.mapper;

import com.juphoon.rtc.datacenter.TestApplication;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.binlog.mapper.flash.FlashEventLogMapper;
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
public class FlashEventLogMapperTest {


    public static EventContext randomEventContext() {
        EventContext ec = new EventContext();
        String random = UUID.randomUUID().toString();

        ec.setFrom(random);
        ec.setRequestId(random);
        ec.setProcessorId("test");
        ec.setEvent(randomEvent());

        return ec;
    }

    public static Event randomEvent() {
        return Event.builder().domainId(100645).appId(0).type(1).number(2).timestamp(System.currentTimeMillis())
                .uuid(UUID.randomUUID().toString()).params(new HashMap<String, Object>() {{
                    put("a", "b");
                    put("b", "b");
                }}).build();
    }

    @Autowired
    private FlashEventLogMapper logMapper;


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
        EventBinLogPO po = EventBinLogPO.fromEventContext(randomEventContext());

        int ret = logMapper.save(po);

        log.info("ret:{}", ret);
        log.info("id:{}", po.getId());
    }

//    @Ignore
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
}
