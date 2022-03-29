package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;


/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:37
 * @Description:
 * @update 1. 2022-03-29. ajian.zheng 抽象公共逻辑，将集合名放到上层，公共handle逻辑放到抽象层
 */
@Slf4j
public abstract class AbstractMongoHandler extends AbstractEventHandler {

    @Autowired
    private MongoTemplate mongoTemplate;

    /// TODO 日志优化以及异常处理

    @Override
    public boolean handle(EventContext ec) {
        String collectionName = collectionName(ec);

        log.info("ec:{},collectionName:{}", ec, collectionName);

        try {

            mongoTemplate.insert(ec.getEvent(), collectionName);
        } catch (DataAccessException e) {
            log.error("DataAccessException:{}", e);
            return false;
        } catch (Exception e) {
            log.error("{}", e);
            return true;
        }
        log.info("执行MongoHandle结束");
        return true;
    }

    /**
     * 集合名
     *
     * @param ec
     * @return
     */
    public abstract String collectionName(EventContext ec);
}
