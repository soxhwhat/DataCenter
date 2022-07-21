package com.juphoon.rtc.datacenter.servicecore.handle.mongo;


import com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.servicecore.api.WindowLevelEnum;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorConcurrentPO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.ROOM_BEAT;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_CONCURRENT_ITEM_ROOM;
import static com.juphoon.rtc.datacenter.servicecore.api.WindowLevelEnum.WIN_MIN;

/**
 * <p>音视频质量监测处理handler,用于实时房间并发数、实时房间同时在线人数指标</p>
 *
 * @author Jiahui.Huang
 * @date 2022-07-17
 * @description 实时房间并发数、实时房间同时在线人数事件窗口内的最大值、最小值、平均值、总和的统计
 */
@Slf4j
@Component
@Setter
@Getter
@ConfigurationProperties(prefix = JrtcDataCenterConstant.DATA_CENTER_WINDOW_LEVEL_CONFIG_PREFIX)
public class MonitorConcurrentStateMongoHandler extends AbstractMongoHandler<StateContext> {
    /**
     * 窗口大小,默认为1分钟窗口
     */
    private WindowLevelEnum size = WIN_MIN;

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.MonitorConcurrentStateMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(ROOM_BEAT);
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }


    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_CONCURRENT_ITEM_ROOM;
    }

    @Override
    public boolean collectionStorageDaily() {
        return false;
    }

    @Override
    public boolean handle(StateContext context) throws Exception {
        String collectionName = getCollectionName(this, context);
        log.debug("context:{}", context);

        MonitorConcurrentPO po = MonitorConcurrentPO.fromState(context);

        long window = getSize().getTime();
        String time = DateFormatUtils.format(new Date(po.getTimestamp() - po.getTimestamp() % window), "yyyy-MM-dd hh:mm:ss");
        log.debug("po:{}", po);
        mongoTemplate.upsert(Query.query(Criteria.where("time").is(time)
                        .and("domainId").is(po.getDomainId())
                        .and("appId").is(po.getAppId())
                        .and("from").is(po.getFrom())),
                Update.update("time", time)
                        .setOnInsert("from", po.getFrom())
                        .setOnInsert("domainId", po.getDomainId())
                        .setOnInsert("appId", po.getAppId())
                        .inc("count", 1)
                        .inc("totalActor", po.getActor())
                        .inc("totalRoom", po.getRoom())
                        .max("maxActor", po.getActor())
                        .min("minActor", po.getActor())
                        .max("maxRoom", po.getRoom())
                        .min("minRoom", po.getRoom()),
                collectionName);

        return true;
    }

}
