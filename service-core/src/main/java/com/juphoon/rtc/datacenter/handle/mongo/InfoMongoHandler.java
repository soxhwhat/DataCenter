package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.INFO_EVENT;
import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.UNDERLINE_TIMESTAMP;

/**
 * info事件数据处理
 *
 * @author zhiwei.zhai
 * @date 2022-05-31
 */
@Slf4j
@Component
public class InfoMongoHandler extends AbstractMongoHandler {

    @Autowired
    @Qualifier("logMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.InfoMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                INFO_EVENT
        );
    }

    @Override
    public String collectionName(EventContext ec) {
        return "info_collection";
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    private static final String EXPIRE_NAME = "expire_timestamp";

    @PostConstruct
    public void createIndex(){
        IndexOperations ops = mongoTemplate().indexOps(collectionName(null));
        Index index = new Index();
        index.named(EXPIRE_NAME);
        index.expire(Duration.ofDays(7));
        index.on(UNDERLINE_TIMESTAMP, Sort.Direction.ASC);
        try {
            ops.ensureIndex(index);
        } catch (Exception e){
            log.info("索引已存在:{}",EXPIRE_NAME);
        }
    }

    @Override
    public boolean handle(EventContext ec) {
        String collectionName = collectionName(ec);
        try {
            mongoTemplate().insert(ec.getEvent().getParams().values(), collectionName);
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
