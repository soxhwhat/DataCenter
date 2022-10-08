package com.juphoon.rtc.datacenter.servicecore.handle.mongo;

import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.servicecore.api.WindowLevelEnum;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorAcdQueueCountPO;
import com.juphoon.rtc.datacenter.servicecore.mapper.MonitorAcdQueueCountMapper;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.model.IndexOptions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_ACD_QUEUE_COUNT;
import static com.juphoon.rtc.datacenter.servicecore.api.WindowLevelEnum.WIN_MIN;

/**
 * <p>视频客服排队机 队列监控</p>
 *
 * @author Jiahui.Huang
 * @date 2022-09-28
 * @description FlowStatusJson(
 *      uniqueId = 7000,
 *      type = 13,
 *      status = 0,
 *      params = {
 *         "from": "#CcAcd@CcAcd.Main2-0.Main",
 *         "queue": "7000",
 *         "timestamp": 1655263216229,
 *         "waitCount": 0
 *     },
 *     domainId=103296,
 *     appId=1
 * )
 *
 */
@Slf4j
@Component
@Data
public class MonitorAcdQueueCountMongoHandler extends AbstractMongoHandler<StateContext> {

    /**
     * 窗口大小,默认为1分钟窗口
     */
    private WindowLevelEnum size = WIN_MIN;

    @Autowired
    private MonitorAcdQueueCountMapper monitorAcdQueueMapper;

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.QUEUE_WAIT_BEAT, EventType.QUEUE_RING_BEAT, EventType.QUEUE_CALL_BEAT);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.MonitorAcdQueueCountMongoHandler;
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_ACD_QUEUE_COUNT;
    }

    @Override
    public boolean collectionStorageDaily() {
        return true;
    }

    public final static AtomicInteger COUNTER = new AtomicInteger(0);

    @Override
    public boolean handle(StateContext context) throws Exception {
        log.debug("context:{}", context);
        String collectionName = getCollectionName(this, context);
        MonitorAcdQueueCountPO po = MonitorAcdQueueCountPO.fromState(context);
        log.debug("po:{}", po);

        long window = getSize().getTime();
        long timestamp = po.getTimestamp() - po.getTimestamp() % window;
        String str = timestamp + po.getQueue() +
                "|" + po.getNumber() + "|" + po.getDomainId() +
                "|" + po.getAppId() + "|" + po.getType();
        String uniqueKey = DigestUtils.md5Hex(str).substring(8, 24);

        mongoTemplate.upsert(Query.query(Criteria.where("uniqueKey").is(uniqueKey)
                        .and("from").is(po.getFrom())),
                Update.update("time", timestamp)
                        .set("domainId", po.getDomainId())
                        .set("appId", po.getAppId())
                        .set("number", po.getNumber())
                        .set("type", po.getType())
                        .set("queue", po.getQueue())
                        .set("count", po.getCount()),
                collectionName);
        try {
            Integer totalCount = mongoTemplate.find(Query.query(Criteria.where("time").is(timestamp)
                                    .and("domainId").is(po.getDomainId())
                                    .and("appId").is(po.getAppId())
                                    .and("from").ne("total")
                                    .and("type").is(po.getType())
                                    .and("number").is(po.getNumber())
                                    .and("queue").is(po.getQueue())),
                            MonitorAcdQueueCountPO.class, collectionName)
                    .stream()
                    .map(MonitorAcdQueueCountPO::getCount)
                    .reduce(0, Integer::sum);

            mongoTemplate.upsert(Query.query(Criteria.where("uniqueKey").is(uniqueKey)
                            .and("from").is("total")),
                    Update.update("time", timestamp)
                            .set("count", totalCount)
                            .set("domainId", po.getDomainId())
                            .set("appId", po.getAppId())
                            .set("number", po.getNumber())
                            .set("type", po.getType())
                            .set("queue", po.getQueue())
                            .inc("times", 1)
                            .inc("total", totalCount)
                            .max("maxCount", totalCount)
                            .min("minCount", totalCount),
                    collectionName);
            COUNTER.incrementAndGet();
        } catch (Exception e) {
            log.error("MonitorAcdQueueCountMongoHandler error", e);
        }


        return true;
    }


}