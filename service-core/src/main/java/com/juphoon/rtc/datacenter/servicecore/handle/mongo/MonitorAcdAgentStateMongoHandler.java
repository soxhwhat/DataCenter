package com.juphoon.rtc.datacenter.servicecore.handle.mongo;

import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorAcdAgentStatePO;
import com.juphoon.rtc.datacenter.servicecore.mapper.MonitorAcdAgentStateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_ACD_AGENT_STATE;

/**
 * @author Jiahui.Huang
 * @date 2022-09-18
 * @description
 *
 * 状态数据上报:FlowStatusJson(
 *    uniqueId=[username:10637048@103291.cloud.justalk.com],
 *    type=12,
 *    status=0,
 *    params={
 *       "acdId" : "#CcOm@CcOm.Main2-0.Main",
 *       "agentId" : "[username:10637048@103291.cloud.justalk.com]",
 *       "agentStatus" : 1,
 *       "checkInTimestamp" : 1655254469663,
 *       "updateTimestamp" : 1655263195948
 *    },
 *    domainId=103291,
 *    appId=0
 * )
 */
@Slf4j
@Component
public class MonitorAcdAgentStateMongoHandler extends AbstractMongoHandler<StateContext> {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(EventType.STAFF_BEAT);
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_ACD_AGENT_STATE;
    }

    @Override
    public boolean collectionStorageDaily() {
        return true;
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.MonitorAcdAgentStateHandler;
    }

    @Override
    public boolean handle(StateContext context) throws Exception {
        log.debug("context:{}", context);
        String collectionName = getCollectionName(this, context);
        MonitorAcdAgentStatePO po = MonitorAcdAgentStatePO.fromState(context);

        log.debug("po:{}", po);

        // insert
        mongoTemplate.insert(po, collectionName);

        return true;
    }
}
