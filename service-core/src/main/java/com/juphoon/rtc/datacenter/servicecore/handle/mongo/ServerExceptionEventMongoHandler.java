package com.juphoon.rtc.datacenter.servicecore.handle.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import com.juphoon.rtc.def.event.entity.alarm.ServerExceptionEvent;
import com.juphoon.rtc.def.persistent.alarm.ExceptionEventPO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_MD;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.SERVER_EXCEPTION;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_SERVER_EXCEPTION_EVENT;

/**
 * <p>终端埋点事件处理handler</p>
 *
 * @author ke.wang
 * @date 2022-04-21
 */
@Slf4j
@Component
public class ServerExceptionEventMongoHandler extends AbstractMongoHandler<EventContext> {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_MD)
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.ServerExceptionEventMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                SERVER_EXCEPTION
        );
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public MongoCollectionEnum collectionName() {
        return COLLECTION_SERVER_EXCEPTION_EVENT;
    }

    @Override
    public boolean handle(EventContext eventContext) throws Exception {
        ExceptionEventPO po = toPO(toRealEvent(eventContext.getEvent()));

        mongoTemplate.insert(po);

        return true;
    }

    @SneakyThrows
    public ServerExceptionEvent toRealEvent(Event event) {
        ServerExceptionEvent realEvent = new ServerExceptionEvent();

        realEvent.setDomainId(event.domainId());
        realEvent.setAppId(event.appId());
        realEvent.setFromTimestamp(event.getTimestamp());
        realEvent.setUuid(event.getUuid());

        ObjectMapper objectMapper = new ObjectMapper();
        ServerExceptionEvent.Content alarmContent = objectMapper.readValue(event.getStringParams(), ServerExceptionEvent.Content.class);

        realEvent.setParams(alarmContent);

        return realEvent;
    }

    public ExceptionEventPO toPO(ServerExceptionEvent event) {
        ExceptionEventPO po = new ExceptionEventPO();

        // 注意, 这里是fromTimestamp, 因为timestamp是Spring Event的字段，被定义为final了
        po.setTimestamp(event.getFromTimestamp());
        po.setDomainId(event.getDomainId());
        po.setAppId(event.getAppId());

        po.setEventCode(event.getParams().getEventCode());
        po.setDomainCode(event.getParams().getDomainCode());
        po.setHost(event.getParams().getHost());
        po.setIgnoreCount(event.getParams().getIgnoreCount());
        po.setLevel(event.getParams().getLevel());
        po.setLocation(event.getParams().getLocation());
        po.setState(event.getParams().getState());
        po.setMessage(event.getParams().getMessage());
        po.setTags(event.getParams().getTags());
        po.setUuid(event.getUuid());

        return po;
    }
}
