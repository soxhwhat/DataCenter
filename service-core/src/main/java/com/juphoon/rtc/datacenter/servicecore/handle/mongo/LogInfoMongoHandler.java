package com.juphoon.rtc.datacenter.servicecore.handle.mongo;

import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.LogContext;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity.MongoLogInfoPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_LOG;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.CLIENT_LOG_INFO_EVENT;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_LOG_INFO;

/**
 * info事件数据处理
 *
 * @author zhiwei.zhai
 * @date 2022-05-31
 */
@Slf4j
@Component
public class LogInfoMongoHandler extends AbstractMongoHandler<LogContext> {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_LOG)
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.LogInfoMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(
                CLIENT_LOG_INFO_EVENT
        );
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_LOG_INFO;
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public boolean handle(LogContext context) {
        String collectionName = getCollectionName(this, context);

        log.debug("context:{},collectionName:{}", context, collectionName);

        MongoLogInfoPO po = MongoLogInfoPO.fromString(context.getLogs().get(0));

        try {
            mongoTemplate().insert(po);
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
