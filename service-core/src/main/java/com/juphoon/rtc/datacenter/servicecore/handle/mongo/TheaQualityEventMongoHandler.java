package com.juphoon.rtc.datacenter.servicecore.handle.mongo;


import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaJoinMonitorPO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaQualityMonitorPO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.THEA_MONITOR_DATA;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_EVENT_THEA_JOIN;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_EVENT_THEA_QUALITY;

/**
 * <p>音视频质量监测处理handler,用于统计卡顿率、优质传输率等指标</p>
 *
 * @author Jiahui.Huang
 * @date 2022-07-11
 */
@Slf4j
@Component
@Setter
public class TheaQualityEventMongoHandler extends AbstractMongoEventHandler implements IMongoCollectionManager {
    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.TheaQualityEventMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(THEA_MONITOR_DATA);
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }


    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_EVENT_THEA_QUALITY;
    }

    @Override
    public boolean collectionStorageDaily() {
        return false;
    }

    @Override
    public boolean handle(EventContext ec) {
        String collectionName = getCollectionName(this, ec);
        String joinName = COLLECTION_EVENT_THEA_JOIN.getName();

        TheaQualityMonitorPO po = TheaQualityMonitorPO.fromEvent(ec);
        TheaJoinMonitorPO joinPo = TheaJoinMonitorPO.fromEvent(ec);

        try {
            //根据po的date字段进行upsert操作，如果存在这条记录，给相同字段添加po对应字段值，否则插入新记录
            mongoTemplate.upsert(Query.query(Criteria.where("date").is(po.getDate())
                            .and("domainId").is(po.getDomainId())
                            .and("appId").is(po.getAppId())
                            .and("type").is(po.getType())),
                    Update.update("date", po.getDate())
                            .setOnInsert("domainId", po.getDomainId())
                            .setOnInsert("appId", po.getAppId())
                            .setOnInsert("type", po.getType())
                            .inc("aMosLowCount", po.getAMosLowCount())
                            .inc("tMosLowCount", po.getTMosLowCount())
                            .inc("totalAMosCount", po.getTotalAMosCount())
                            .inc("totalTMosCount", po.getTotalTMosCount())
                            .inc("unLossCount", po.getUnLossCount())
                            .inc("lossTotalCount", po.getLossTotalCount())
                            .inc("suRtt", po.getSuRtt())
                            .inc("rttTotalCount", po.getRttTotalCount())
                            .inc("suJitter", po.getSuJitter())
                            .inc("sdJitter", po.getSdJitter())
                            .inc("suJitterTotalCount", po.getSuJitterTotalCount())
                            .inc("sdJitterTotalCount", po.getSdJitterTotalCount()),
                    collectionName);

            mongoTemplate.insert(joinPo, joinName);

        } catch (DataAccessException e) {
            log.error("DataAccessException:{}", e);
            return false;
        } catch (Exception e) {
            log.error("{}", e);
            return true;
        }
        return true;
    }

}
