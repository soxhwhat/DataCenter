package com.juphoon.rtc.datacenter.dist.h20.handler;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.dist.h20.entity.KafkaTicketPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.INNER_TICKET_ACD;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.TICKER_COMPLETE_VOLTE;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/7/22 17:26
 */
@Slf4j
@Component
public class H20AcdTicketEventMongoHandler extends H20AbstractMongoHandler {

    @Autowired
    @Qualifier("recordMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.H20AcdTicketEventMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(TICKER_COMPLETE_VOLTE);
    }

    @Override
    public KafkaTicketPo buildKafkaTicketPo(Event event) {
        KafkaTicketPo po = new KafkaTicketPo();
        po.fromEvent(event);
        return po;
    }

    @Override
    public EventType productEventType() {
        return INNER_TICKET_ACD;
    }

    @Override
    public String collectionName() {
        return "jrtc.records";
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }


}
