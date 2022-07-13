package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.entity.po.thea.TheaRecvPO;
import com.juphoon.rtc.datacenter.entity.po.thea.TheaSendPO;
import com.mongodb.bulk.BulkWriteResult;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.api.EventType.THEA_MONITOR_DATA;
import static com.juphoon.rtc.datacenter.api.MongoCollectionEnum.*;

/**
 * <p>天赛音视频上下行通话质量handler</p>
 *
 * @author Jiahui Huang
 * @date 2022-07-08
 */
@Slf4j
@Component
@Setter
public class TheaMonitorEventMongoHandler extends AbstractMongoEventHandler implements IMongoCollectionManager {
    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.TheaMonitorEventMongoHandler;
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
        return COLLECTION_EVENT_THEA;
    }


    @Override
    public boolean handle(EventContext ec) {
        String recvCollection = COLLECTION_EVENT_THEA_RECV.getName() + DateFormatUtils.format(new Date(ec.getCreatedTimestamp()), "yyyyMMdd");
        String sendCollection = COLLECTION_EVENT_THEA_SEND.getName() + DateFormatUtils.format(new Date(ec.getCreatedTimestamp()), "yyyyMMdd");

        try {
            List<TheaRecvPO> theaRecvPos = TheaRecvPO.fromEvent(ec);
            TheaSendPO theaSendPo = TheaSendPO.fromEvent(ec);
            //遍历每个对象，插入到对应的集合中
            BulkOperations operations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, recvCollection);
            operations.insert(theaRecvPos);
            BulkWriteResult result = operations.execute();
            mongoTemplate().insert(theaSendPo, sendCollection);
        } catch (DataAccessException e) {
            log.error("DataAccessException={}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Exception={}", e.getMessage());
            return false;
        }
        return true;
    }


}
