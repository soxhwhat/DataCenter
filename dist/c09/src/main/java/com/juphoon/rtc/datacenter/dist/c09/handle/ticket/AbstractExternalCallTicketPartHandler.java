package com.juphoon.rtc.datacenter.dist.c09.handle.ticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.ticket.ExternalCallTicketPartPO;
import com.juphoon.rtc.datacenter.dist.c09.handle.C09AbstractMongoEventHandler;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.TICKER_COMPLETE;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_EVENT_EXTERNAL_TICKET_PART;

/**
 * <p>宁波客服话单事件抽象handler</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 用于统计宁波银行客服话单通话成功率、等待时长、通话时长
 */
@Slf4j
public abstract class AbstractExternalCallTicketPartHandler extends C09AbstractMongoEventHandler<ExternalCallTicketPartPO> {
    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(TICKER_COMPLETE);
    }

    @Override
    public ExternalCallTicketPartPO poFromEvent(Event event) {
        ExternalCallTicketPartPO po = new ExternalCallTicketPartPO();
        try {
            po.fromEvent(event);
            po.setStatType(statType().getStatType());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        po.setStatType(statType().getStatType());
        return po;
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public boolean collectionStorageDaily() {
        return false;
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_EVENT_EXTERNAL_TICKET_PART;
    }

    @Override
    public boolean handle(EventContext ec){
        Event event = ec.getEvent();
        ExternalCallTicketPartPO po = poFromEvent(event);
        String collectionName = getCollectionName(this, ec);

        Long timestamp = po.getTimestamp() - po.getTimestamp() %  statType().getInterval();

        mongoTemplate.upsert(Query.query(Criteria.where("uniqueKey").is(po.getUniqueKey())
                        .and("timestamp").is(timestamp)),
                Update.update("timestamp", timestamp)
                        .setOnInsert("domainId", po.getDomainId())
                        .setOnInsert("appId", po.getAppId())
                        .setOnInsert("statType", po.getStatType())
                        .inc("waitTime", po.getWaitTime())
                        .inc("talkTime", po.getTalkTime())
                        .inc("totalCount", po.getTotalCount())
                        .inc("successCount", po.getSuccessCount())
                        .inc("cnt", po.getCnt()),
                collectionName);

        return true;
    }
}
