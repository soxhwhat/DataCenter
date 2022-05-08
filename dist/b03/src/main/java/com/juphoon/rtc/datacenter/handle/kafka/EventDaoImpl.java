package com.juphoon.rtc.datacenter.handle.kafka;

import com.juphoon.rtc.datacenter.entity.EventMapPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/5/6 18:33
 * @Description:
 */
@Component
public class EventDaoImpl {

    public final static String FIELD_PARAMS_BEGINTIMESTAMP = "params.beginTimestamp";
    public final static String FIELD_PARAMS_CALLID = "params.callId";
    public final static String TICKET_TOPIC = "jrtc.events";

    @Autowired
    @Qualifier("eventMongoTemplate")
    private MongoTemplate eventMongoTemplate;

    public List<EventMapPO> getRelationEventList(String callId) {
        Query query = new Query();
        Criteria criteria = Criteria.where(FIELD_PARAMS_CALLID).is(callId);
        query.getSortObject().put(FIELD_PARAMS_BEGINTIMESTAMP, -1);
        query.addCriteria(criteria);
        return eventMongoTemplate.find(query, EventMapPO.class, TICKET_TOPIC);
    }
}
