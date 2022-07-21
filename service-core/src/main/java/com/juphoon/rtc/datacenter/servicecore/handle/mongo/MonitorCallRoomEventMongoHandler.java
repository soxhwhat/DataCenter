package com.juphoon.rtc.datacenter.servicecore.handle.mongo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorCallRoomPO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.ROOM_DESTROY;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.*;

/**
 * <p>平台创建、销毁房间处理Handler</p>
 *
 * @author Jiahui.Huang
 * @date 2022-07-17
 * @description 用于生成话单列表
 */
@Slf4j
@Component
@Setter
public class MonitorCallRoomEventMongoHandler extends AbstractMongoEventHandler {
    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.MonitorCallRoomEventMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(ROOM_DESTROY);
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }


    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_EVENT_ROOM;
    }

    @Override
    public boolean collectionStorageDaily() {
        return false;
    }

    @Override
    public boolean handle(EventContext context) {
        log.debug("context:{}", context);

        try {
            MonitorCallRoomPO po = MonitorCallRoomPO.fromEvent(context);
            mongoTemplate.insert(po);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return true;
    }

}
