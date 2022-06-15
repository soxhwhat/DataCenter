package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handle.mongo.entity.MongoEventPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;

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
public abstract class AbstractMongoEventHandler extends AbstractMongoHandler<EventContext> {
    @Override
    public boolean handle(EventContext ec) {
        String collectionName = IMongoCollectionManager.getCollectionName(this, ec);

        log.info("ec:{},collectionName:{}", ec, collectionName);

        try {
            MongoEventPO po = MongoEventPO.fromEvent(ec.getEvent());
//            mongoTemplate().insert(po, collectionName);
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
