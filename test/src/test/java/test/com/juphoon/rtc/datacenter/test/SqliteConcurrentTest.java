package test.com.juphoon.rtc.datacenter.test;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.service.EventService;
import com.juphoon.rtc.datacenter.test.TestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.juphoon.rtc.datacenter.test.handler.TestLastCounterHandler.COUNTER;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author haijie.liang@juphoon.com
 * @date 2022/8/23 13:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("random")
public class SqliteConcurrentTest {
    @Autowired
    private EventService eventService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {

    }


    @Test
//    @Ignore
    public void sqliteConcurrentTest() throws InterruptedException {
        int MAX = 20000;
        String msg = "test";
        long begin = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < MAX; i++) {
            executor.submit(() -> {
                try {
//                    mockMvc.perform(MockMvcRequestBuilders.get("/event")
//                                    .accept(MediaType.APPLICATION_JSON)
//                                    .param("msg", "hello"))
//                            .andExpect(MockMvcResultMatchers.status().isOk())
//                            .andReturn();
//                    log.info(mvcResult.getResponse().getContentAsString());
//                    log.info("get msg:{}", msg);
//
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
    }
}
