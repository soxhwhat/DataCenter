package test.com.juphoon.rtc.datacenter.test;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.SqliteFlashEventLogMapper;
import com.juphoon.rtc.datacenter.datacore.service.EventService;
import com.juphoon.rtc.datacenter.datacore.utils.MetricUtils;
import com.juphoon.rtc.datacenter.test.TestApplication;
import com.juphoon.rtc.datacenter.test.handler.TestEventRandomFailHandler;
import com.juphoon.rtc.datacenter.test.handler.TestLastCounterHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ajian.zheng@juphoon.com
 * @date 7/22/22 5:19 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("redo")
public class EventServiceWithRedoTest {
    @Autowired
    private EventService eventService;

    @Autowired
    private SqliteFlashEventLogMapper sqliteFlashEventLogMapper;

    @SneakyThrows
    @Before
    public void init() {
        sqliteFlashEventLogMapper.dropTable();
        sqliteFlashEventLogMapper.createTable();
    }

    private static final int MAX = 10000;

    /**
     * 为了测试异步慢消费时，事件处理成功率是否能够得到保证
     * 压测用例，仅在正式版本中执行
     *
     * @throws InterruptedException
     */
    @Test
    public void testConcurrentEventServiceCommitLoad() throws InterruptedException {
        Assume.assumeTrue("临时版本跳过压测", "RELEASE".equalsIgnoreCase(System.getProperty("VERSION_TYPE")));

        List<EventBinLogPO> ret = sqliteFlashEventLogMapper.find(10);
        Assert.assertTrue(ret.isEmpty());

        long begin = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < MAX; i++) {
            int domain = i;
            executor.submit(() -> {
                try {
                    Map<String, Object> params = new HashMap<>(1);
                    params.put("k", "v");

                    Event event = Event.builder()
                            .domainId(domain)
                            .appId(0)
                            .type(-1)
                            .number(-1)
                            .timestamp(System.currentTimeMillis())
                            .params(params)
                            .uuid(UUID.randomUUID().toString())
                            .build();

                    EventContext ec = new EventContext(event);
                    ec.setRequestId(event.getUuid());
                    ec.setFrom("test");

                    List<EventContext> data = new ArrayList<>();
                    data.add(ec);

                    eventService.commit(data);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.SECONDS);

        /// 消费完成，或者等待超过10秒，则结束
        while ((TestEventRandomFailHandler.COUNTER.get() < MAX) && (System.currentTimeMillis() - begin < 1000000)) {
            Thread.yield();
        }

        long end = System.currentTimeMillis();

        log.info("publish:{}, process:{}, redo:{}", MAX, TestLastCounterHandler.COUNTER.get(), TestEventRandomFailHandler.COUNTER.get());
        log.info("cost:{}", end - begin);
        log.info("tps:{}", (float) TestLastCounterHandler.COUNTER.get() / (end - begin) * 1000);
        Assert.assertEquals(MAX, TestLastCounterHandler.COUNTER.get());

        do {
            Thread.sleep(1000);
            ret = sqliteFlashEventLogMapper.find(10);
        } while (!ret.isEmpty() && ((System.currentTimeMillis() - end) < 10000));

        Assert.assertTrue(ret.isEmpty());

        MetricUtils.dump();
    }
}
