package test.com.juphoon.rtc.datacenter.servicecore.handler.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.datacore.api.State;
import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorConcurrentPO;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.MonitorConcurrentStateMongoHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.*;
import org.junit.runner.RunWith;
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
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_CONCURRENT_ITEM_ROOM;
import static com.juphoon.rtc.datacenter.servicecore.handle.mongo.MonitorConcurrentStateMongoHandler.COUNTER;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test-mysql")
public class MonitorConcurrentStateMongoHandlerTest{

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @Autowired
    private MonitorConcurrentStateMongoHandler monitorConcurrentStateMongoHandler;
    /**
     * 此处模拟同时有200个服务器上报数据
     */
    private static final int MAX = 200;

    int dayName = Integer.parseInt(DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd"));
    String collectionName = COLLECTION_CONCURRENT_ITEM_ROOM.getName() + dayName;

    Map<String, Object> params;

    @SneakyThrows
    @Before
    public void init() {
        mongoTemplate.remove(new Query(), collectionName);
        String from = "from";

        params = new HashMap<>();
        params.put("appId", 0);
        params.put("concurrentNumber", 1);
        params.put("concurrentPerson", 2);
        params.put("domainId", 0);
        params.put("from", from);
        params.put("resourceType", 0);
        params.put("updateTimeStamp", "1");
    }

    @After
    public void after() {
        mongoTemplate.dropCollection(collectionName);
    }

    /**
     * 转换
     * 态数据上报:FlowStatusJson(uniqueId=JSMS@JSMS.Main0.Main01.Main, type=22, status=0, params={
     *    "appId" : 4,
     *    "concurrentNumber" : 1,
     *    "concurrentPerson" : 2,
     *    "domainId" : 100645,
     *    "from" : "JSMS@JSMS.Main0.Main01.Main",
     *    "resourceType" : 0,
     *    "updateTimeStamp" : "1655773334312"
     * }
     */
    @SneakyThrows
    @Test
    public void handleUnderStress() {
        Assume.assumeTrue("临时版本跳过压测", "RELEASE".equalsIgnoreCase(System.getProperty("VERSION_TYPE")));
        Assert.assertEquals(0, mongoTemplate.getCollection(collectionName).countDocuments());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            executor.submit(() -> {

                try {
                    String random = String.valueOf((int) (Math.random() * MAX));

                    String json = new ObjectMapper().writeValueAsString(params);

                    State state = new State(0,0,random, 22, 0, json);
                    StateContext context = new StateContext(state);

                    monitorConcurrentStateMongoHandler.handle(context);
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
        mongoTemplate.find(new Query(Criteria.where("from").is("total")), MonitorConcurrentPO.class, collectionName).forEach(monitorConcurrentPO -> {
            // 判断并发统计结果是否正确
            Assert.assertEquals(cnt - 1, (int) monitorConcurrentPO.getRoom());

        });

    }
    @Test
    public void handleNormal() throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(params);

        State state = State.builder()
                .type(0)
                .state(0)
                .params(json).build();

        StateContext context = new StateContext(state);

        monitorConcurrentStateMongoHandler.handle(context);

        Assert.assertEquals(2, mongoTemplate.getCollection(collectionName).countDocuments());

    }


}