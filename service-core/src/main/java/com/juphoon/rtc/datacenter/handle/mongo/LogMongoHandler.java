package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.handle.mongo.entity.MongoLogPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.MONGO_TEMPLATE_LOG;
import static com.juphoon.rtc.datacenter.api.EventType.CLIENT_LOG_EVENT;
import static com.juphoon.rtc.datacenter.api.MongoCollectionEnum.COLLECTION_LOG;
import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterEventCode.E_MONGO_HANDLE_ERROR;

/**
 * <p>mongodb操作handler抽象类</p>
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:37
 * @update
 * <li>1. 2022-03-29. ajian.zheng 抽象公共逻辑，将集合名放到上层，公共handle逻辑放到抽象层collectionName</li>
 * <li>2. 2022-03-31. ajian.zheng 支持多数据源，将数据源放到上层，公共handle逻辑放到抽象层mongoTemplate</li>
 */
@Slf4j
@Component
public class LogMongoHandler extends AbstractMongoHandler<LogContext> {
    @Autowired
    @Qualifier(MONGO_TEMPLATE_LOG)
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.LogMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(
                CLIENT_LOG_EVENT
        );
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_LOG;
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public boolean collectionStorageDaily() {
        return true;
    }

    @Override
    public boolean handle(LogContext context) {
        String collectionName = IMongoCollectionManager.getCollectionName(this, context);

        log.info("context:{},collectionName:{}", context, collectionName);

        List<MongoLogPO> list = context.getLogs().stream().map(MongoLogPO::fromString).collect(Collectors.toList());

        try {
            mongoTemplate().insert(list, collectionName);
        } catch (DataAccessException e) {
            log.error("{}", com.juphoon.iron.cube.starter.log.LogContext.serviceEvent(E_MONGO_HANDLE_ERROR).error(e));
            return false;
        } catch (Exception e) {
            log.error("{}", com.juphoon.iron.cube.starter.log.LogContext.serviceEvent(E_MONGO_HANDLE_ERROR).error(e));
            return true;
        }

        return true;
    }


}
