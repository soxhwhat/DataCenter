package com.juphoon.rtc.datacenter.dist.c09.handle.ticket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.*;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.ticket.ExternalCallTicketDailyPO;
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
import static com.juphoon.rtc.datacenter.datacore.api.EventType.TICKER_COMPLETE;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_EVENT_EXTERNAL_TICKET_DAILY;
import static com.juphoon.rtc.datacenter.servicecore.api.WindowLevelEnum.WIN_DAY;

/**
 * <p>宁波外呼push事件处理handler</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 用于统计宁波银行客服话单通话成功率、等待时长、通话时长(1day)
 */
@Slf4j
@Component
@Setter
public class ExternalCallTicketDailyHandler extends C09AbstractMongoEventHandler<ExternalCallTicketDailyPO> {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    MongoTemplate mongoTemplate;

    @Override
    public StatType statType() {
        return StatType.STAT_DAY;
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.ExternalCallTicketDailyHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(TICKER_COMPLETE);
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public ExternalCallTicketDailyPO poFromEvent(Event event) {
        ExternalCallTicketDailyPO po = new ExternalCallTicketDailyPO();
        try {
            po.fromEvent(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return po;
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_EVENT_EXTERNAL_TICKET_DAILY;
    }

    @Override
    public boolean collectionStorageDaily() {
        return false;
    }


    @Override
    public boolean handle(EventContext ec) {
        Event event = ec.getEvent();
        ExternalCallTicketDailyPO po = poFromEvent(event);
        String collectionName = getCollectionName(this, ec);

        Long timestamp = Long.parseLong(DateFormatUtils.format(new Date(po.getTimestamp() - po.getTimestamp() % statType().getInterval()), "yyyyMMdd"));
        mongoTemplate.upsert(Query.query(Criteria.where("uniqueKey").is(po.getUniqueKey())
                        .and("timestamp").is(timestamp)),
                Update.update("timestamp", timestamp)
                        .setOnInsert("domainId", po.getDomainId())
                        .setOnInsert("appId", po.getAppId())
                        .inc("waitTime", po.getWaitTime())
                        .inc("talkTime", po.getTalkTime())
                        .inc("totalCount", po.getTotalCount())
                        .inc("successCount", po.getSuccessCount())
                        .inc("cnt", po.getCnt()),
                collectionName);

        return true;
    }

}
