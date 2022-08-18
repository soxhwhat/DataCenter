package com.juphoon.rtc.datacenter.dist.c09.handle.push;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.*;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.push.ExternalCallPushDailyPO;
import com.juphoon.rtc.datacenter.dist.c09.handle.C09AbstractMongoEventHandler;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.PUSH_EVENT;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_EVENT_EXTERNAL_PUSH_DAILY;

/**
 * <p>宁波外呼push事件处理handler</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 用于统计宁波外呼push成功率
 */
@Slf4j
@Component
@Setter
public class ExternalCallPushDailyHandler extends C09AbstractMongoEventHandler<ExternalCallPushDailyPO> {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    MongoTemplate mongoTemplate;

    @Override
    public StatType statType() {
        return StatType.STAT_DAY;
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.ExternalCallPushDailyHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(PUSH_EVENT);
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public ExternalCallPushDailyPO poFromEvent(Event event) {
        ExternalCallPushDailyPO po = new ExternalCallPushDailyPO();
        try {
            po.fromEvent(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return po;
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_EVENT_EXTERNAL_PUSH_DAILY;
    }

    @Override
    public boolean collectionStorageDaily() {
        return false;
    }


    @Override
    public boolean handle(EventContext ec) {

        Event event = ec.getEvent();
        log.info("ExternalCallPushDailyHandler handle event: {}", event);
        ExternalCallPushDailyPO po = poFromEvent(event);
        String collectionName = getCollectionName(this, ec);

        Long timestamp = Long.parseLong(DateFormatUtils.format(new Date(po.getTimestamp() - po.getTimestamp() % statType().getInterval()), "yyyyMMdd"));

        mongoTemplate.upsert(Query.query(Criteria.where("uniqueKey").is(po.getUniqueKey())
                        .and("timestamp").is(timestamp)),
                Update.update("timestamp", timestamp)
                        .setOnInsert("domainId", po.getDomainId())
                        .setOnInsert("appId", po.getAppId())
                        .inc("totalCount", po.getTotalCount())
                        .inc("successCount", po.getSuccessCount()),
                collectionName);

        return true;
    }

}
