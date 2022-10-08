package test.com.juphoon.rtc.datacenter.servicecore.handler.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.datacore.api.State;
import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorAcdQueueCountPO;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.MonitorAcdQueueCountMongoHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_ACD_QUEUE_COUNT;
import static com.juphoon.rtc.datacenter.servicecore.handle.mongo.MonitorAcdQueueCountMongoHandler.COUNTER;

/**
 * <p>视频客服队列状态人数统计handler测试</p>
 *
 * @author Jiahui.Huang
 * @date 2022-09-28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test-mysql")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MonitorAcdQueueCountMongoHandlerTest {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @Autowired
    private MonitorAcdQueueCountMongoHandler monitorAcdQueueCountMongoHandler;

    private static final int MAX = 100;

    int dayName = Integer.parseInt(DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd"));

    String collectionName = COLLECTION_ACD_QUEUE_COUNT.getName() + dayName;

    Map<String, Object> params;

    @SneakyThrows
    @Before
    public void init() {
        mongoTemplate.remove(new Query(), collectionName);
        String agentId = "zhangsan";
        params = new HashMap<>();
        params.put("queue", "7000");
        params.put("timestamp", "1");
        params.put("callCount", 1);

    }

    @After
    public void after() {
        mongoTemplate.dropCollection(collectionName);
    }

    @SneakyThrows
    @Test
    public void testFirstHandleUnderStress() {
        Assume.assumeTrue("临时版本跳过压测", "RELEASE".equalsIgnoreCase(System.getProperty("VERSION_TYPE")));
        Assert.assertEquals(0, mongoTemplate.getCollection(collectionName).countDocuments());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            executor.submit(() -> {
                try {
                    int random = (int) (Math.random() * 100);
                    params.put("from", "#CcOm@CcOm.Main2-0.Main" + random);
                    String json = new ObjectMapper().writeValueAsString(params);

                    State state1 = State.builder()
                            .uuid("uuid")
                            .type(13)
                            .state(2)
                            .params(json).build();

                    StateContext ctx1 = new StateContext(state1);
                    monitorAcdQueueCountMongoHandler.handle(ctx1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.SECONDS);

        // 消费完成，或者等待超过10秒，则结束
        while ((COUNTER.get() < MAX) && (System.currentTimeMillis() - begin < 10000)) {
            Thread.yield();
        }
        // 判断handler是否完整执行了MAX次
        Assert.assertEquals(MAX, COUNTER.get());
        int cnt = (int) mongoTemplate.getCollection(collectionName).countDocuments();
        mongoTemplate.find(new Query(Criteria.where("from").is("total")), MonitorAcdQueueCountPO.class, collectionName).forEach(po -> {
            // 判断并发统计结果是否正确
            Assert.assertEquals(cnt - 1, (int) po.getCount());
        });
    }

    @Test
    public void testSecondHandleNormal() throws Exception {
        params.put("from", "#CcOm@CcOm.Main2-0.Main");
        String json = new ObjectMapper().writeValueAsString(params);

        State state1 = State.builder()
                .uuid("uuid")
                .type(13)
                .state(2)
                .params(json).build();

        StateContext ctx1 = new StateContext(state1);
        monitorAcdQueueCountMongoHandler.handle(ctx1);

        Assert.assertEquals(2, mongoTemplate.getCollection(collectionName).countDocuments());

    }
}