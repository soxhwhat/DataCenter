package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.entity.EventMapPO;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

/**
 * <p>mongodb操作handler抽象类</p>
 *
 * @author Yuan wenjun
 * @date 2022/04/28
 * @update <li>1. 2022-03-29. ajian.zheng 抽象公共逻辑，将集合名放到上层，公共handle逻辑放到抽象层collectionName</li>
 * <li>2. 2022-03-31. ajian.zheng 支持多数据源，将数据源放到上层，公共handle逻辑放到抽象层mongoTemplate</li>
 */
@Slf4j
public abstract class AbstractMongoHandler extends AbstractEventHandler {

    public final static String FIELD_PARAMS_BEGINTIMESTAMP="params.beginTimestamp";
    public final static String FIELD_PARAMS_CALLID="params.callId";

    @Override
    public boolean handle(EventContext ec) {
        String collectionName = collectionName();

        log.info("ec:{},collectionName:{}", ec, collectionName);

        try {
            mongoTemplate().insert(getEventMapPo(ec.getEvent()), collectionName);
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

    private EventMapPO getEventMapPo(Event event) {
        EventMapPO eventMapPo = new EventMapPO();
        BeanUtils.copyProperties(event, eventMapPo);
        eventMapPo.setEventNumber(event.eventNumber());
        eventMapPo.setDomainId(event.domainId());
        eventMapPo.setAppId(event.appId());
        eventMapPo.setType(event.eventType());
        eventMapPo.setTimestamp(event.timestamp());
        return eventMapPo;
    }

    @PostConstruct
    public void init(){
        Set<String> indexKeyMap = new ConcurrentSkipListSet<>();
        mongoTemplate().indexOps(collectionName()).getIndexInfo().forEach(v -> v.getIndexFields().forEach(index -> indexKeyMap.add(index.getKey())));
        Map<String, String> indexMap = checkIndex(indexKeyMap);
        indexMap.forEach(this::createIndex);
    }

    protected Map<String, String> checkIndex(Set<String> indexKeyMap) {
        Map<String, String> indexMap = new HashMap<>(1);
        if (!indexKeyMap.contains(FIELD_PARAMS_BEGINTIMESTAMP)) {
            indexMap.put(collectionName(), FIELD_PARAMS_BEGINTIMESTAMP);
        }
        return indexMap;
    }

    private void createIndex(String collectionName, String key) {
        Index keyIndex = new Index();
        keyIndex.on(key, Sort.Direction.ASC);
        mongoTemplate().indexOps(collectionName).ensureIndex(keyIndex);
    }

    /**
     * 集合名
     *
     * @return
     */
    public abstract String collectionName();

    /**
     * 获取mongodb模板
     *
     * @return
     */
    public abstract MongoTemplate mongoTemplate();
}
