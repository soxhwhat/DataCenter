package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.entity.po.thea.TheaQualityMonitorPO;
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

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.api.EventType.THEA_MONITOR_DATA;
import static com.juphoon.rtc.datacenter.api.MongoCollectionEnum.COLLECTION_EVENT_THEA_QUALITY;

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
        String collectionName = IMongoCollectionManager.getCollectionName(this, ec);
        log.info("TheaQualityMongoHandler collectionName:{}", collectionName);

        TheaQualityMonitorPO po = TheaQualityMonitorPO.fromEvent(ec);

        try {
            //根据po的date字段进行upsert操作，如果存在这条记录，给相同字段添加po对应字段值，否则插入新记录
             mongoTemplate.upsert(Query.query(Criteria.where("date").is(po.getDate())),
                      Update.update("date", po.getDate())
                              //TODO
                              //如果得到的aMosLowCount值是0，则不进行累加，否则需要累加
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
