package com.juphoon.rtc.datacenter.dist.h20.handler;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.handler.AbstractEventHandler;
import com.juphoon.rtc.datacenter.dist.h20.entity.KafkaTicketPo;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity.MongoEventPO;
import com.juphoon.rtc.datacenter.servicecore.service.impl.RedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/7/22 17:26
 */
@Slf4j
public abstract class H20AbstractMongoHandler extends AbstractEventHandler {

    @Autowired
    protected RedisServiceImpl cacheService;

    @Override
    public boolean handle(EventContext ec) {
        try {
            mongoTemplate().insert(poFromEvent(ec.getEvent()), collectionName());

            KafkaTicketPo kafkaTicketPo = buildKafkaTicketPo(ec.getEvent());
            log.info("KafkaTicketPo[{}]", kafkaTicketPo.toString());
            insertRedis(kafkaTicketPo);

            EventContext context = buildEventContext(ec.getEvent());
            getProcessor().commit(context);
            log.info("send context[{}]", context.dump());
            log.info("执行MongoHandle结束");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return true;
    }

    /**
     * 写入redis
     *
     * @param po
     */
    public void insertRedis(KafkaTicketPo po) {
        cacheService.hPut(productEventType().name(), po.getCallId(), po);
    }

    /**
     * 构建对应具体的po
     *
     * @param event
     * @return
     */
    public MongoEventPO poFromEvent(Event event) {
        MongoEventPO t = new MongoEventPO();
        t.fromEvent(event);
        return t;
    }

    /**
     * 构造KafkaTicketPo
     *
     * @param event
     * @return
     */
    public abstract KafkaTicketPo buildKafkaTicketPo(Event event);

    public EventContext buildEventContext(Event event) {
        String magic = UUID.randomUUID().toString();
        String hostname = "Unknown";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        Map<String, Object> params = new HashMap<>(1);
        params.put("callId", event.getParams().get("callId"));
        String host = hostname;
        EventContext ec = new EventContext(Event.builder()
                .domainId(event.getDomainId())
                .appId(event.getAppId())
                .type(productEventType().getType())
                .number(productEventType().getNumber())
                .timestamp(System.currentTimeMillis())
                .params(params)
                .uuid(event.getUuid())
                .build());

        ec.setRequestId(magic);
        ec.setFrom(host);
        return ec;
    }

    /**
     * 产生的事件类型
     *
     * @return
     */
    public abstract EventType productEventType();

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
