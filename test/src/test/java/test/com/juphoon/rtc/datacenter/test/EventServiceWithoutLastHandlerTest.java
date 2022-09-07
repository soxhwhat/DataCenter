package test.com.juphoon.rtc.datacenter.test;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FlashEventLogMapper;
import com.juphoon.rtc.datacenter.datacore.service.EventService;
import com.juphoon.rtc.datacenter.datacore.utils.MetricUtils;
import com.juphoon.rtc.datacenter.datacore.utils.TestUtils;
import com.juphoon.rtc.datacenter.test.TestApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.juphoon.rtc.datacenter.test.handler.TestLastCounterHandler.COUNTER;

/**
 * <p>EventServiceWithoutLastHandlerTest</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 7/22/22 5:19 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("concurrent")
public class EventServiceWithoutLastHandlerTest {
    @Autowired
    private EventService eventService;

    @Autowired
    private FlashEventLogMapper flashEventLogMapper;

    @SneakyThrows
    @Before
    public void init() {
        flashEventLogMapper.dropTable();
        flashEventLogMapper.createTable();
    }

    @After
    public void after() {
        flashEventLogMapper.dropTable();
    }

    private static final int MAX = 100000;

    @Test
    public void testMultiThread() {
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        log.info("*** ready~~ ");
        log.info("*** GO!");

        long begin = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            // 执行任务
            threadPool.execute(() -> {
                System.out.println("任务被执行,线程:" + Thread.currentThread().getName());

                EventContext context = TestUtils.randomEventContext();
                eventService.commit(context);
            });
        }

        long end = System.currentTimeMillis();


        log.info("*** DONE !");

        log.info("*************");
        log.info("** cost:{} **", end - begin);
        log.info("*************");
    }

    /**
     * 压测用例，仅在正式版本中执行
     *
     * @throws InterruptedException
     */
    @Test
    public void testConcurrentEventServiceCommitLoad() throws InterruptedException {
        String versionType = System.getProperty("VERSION_TYPE");
        Assume.assumeTrue("临时版本跳过压测", "RELEASE".equalsIgnoreCase(versionType));

        String msg = "test";

        long begin = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < MAX; i++) {
            executor.submit(() -> {
                try {
                    Map<String, Object> params = new HashMap<>(1);
                    params.put("k", "v");

                    Event event = Event.builder()
                            .domainId(100645)
                            .appId(0)
                            .type(-1)
                            .number(-1)
                            .timestamp(System.currentTimeMillis())
                            .params(params)
                            .uuid(UUID.randomUUID().toString())
                            .build();

                    EventContext ec = new EventContext(event);
                    ec.setRequestId(event.getUuid());
                    ec.setFrom(msg);

                    List<EventContext> data = new ArrayList<>();
                    data.add(ec);

                    eventService.commit(data);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        /// 消费完成，或者等待超过10秒，则结束
        while ((COUNTER.get() < MAX) && (System.currentTimeMillis() - begin < 100000)) {
            Thread.yield();
        }

        long end = System.currentTimeMillis();

        log.info("publish:{}, process:{}", MAX, COUNTER.get());
        log.info("cost:{}", end - begin);
        log.info("tps:{}", (float) COUNTER.get() / (end - begin) * 1000);

        Assert.assertEquals(MAX, COUNTER.get());

        MetricUtils.dump();
    }
}
