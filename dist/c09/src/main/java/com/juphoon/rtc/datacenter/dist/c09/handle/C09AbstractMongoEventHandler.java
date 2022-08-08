package com.juphoon.rtc.datacenter.dist.c09.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.C09CommonPO;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.AbstractMongoHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;

/**
 * <p>宁波银行外呼需求抽象handler</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 */
@Slf4j
public abstract class C09AbstractMongoEventHandler<T extends C09CommonPO> extends AbstractMongoHandler<EventContext> {

    /**
     * 统计类型
     *
     * @return
     */
    public abstract StatType statType();

    @Override
    public boolean handle(EventContext ec) {
        String collectionName = getCollectionName(this, ec);

        log.info("ec:{},collectionName:{}", ec, collectionName);

        try {
            T po = poFromEvent(ec.getEvent());
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

    /**
     * 构建对应具体的po
     *
     * @param event
     * @return
     */
    public T poFromEvent(Event event) {
        T t = (T) new C09CommonPO();
        try {
            t.fromEvent(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

}
