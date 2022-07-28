package com.juphoon.rtc.datacenter.dist.h20.handler;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.dist.h20.entity.KafkaTicketPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.INNER_TICKET_SIP;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.SIP_MEDIA_TICKET;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/7/22 17:24
 */
@Slf4j
@Component
public class H20SipTicketEventMongoHandler extends H20AbstractMongoHandler {

    private static final int HEIGHT_480 = 480;
    private static final int HEIGHT_720 = 720;

    @Autowired
    @Qualifier("recordMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.H20SipTicketEventMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(SIP_MEDIA_TICKET);
    }

    @Override
    public String collectionName() {
        return "jrtc.records";
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public EventType productEventType() {
        return INNER_TICKET_SIP;
    }

    /**
     *  {
     *     "domainId": 100645,
     *     "appId": 0,
     *     "uuid": "5db9f63e-4513-45b9-b44b-0c05e9c27113",
     *     "type": 11,
     *     "number": 2,
     *     "timestamp": 1659409807331,
     *     "params": {
     *         "callId": "1659409807327",
     *         "audio": 1000,
     *         "video": {
     *             "total": 60,
     *             "detail": [
     *                 {
     *                     "width": 1080,
     *                     "time": 30,
     *                     "height": 960
     *                 },
     *                 {
     *                     "width": 480,
     *                     "time": 30,
     *                     "height": 100
     *                 }
     *             ]
     *         }
     *     }
     * }
     * @param event
     * @return
     */
    @Override
    public KafkaTicketPo buildKafkaTicketPo(Event event) {
        int audio = (int) event.getParams().getOrDefault("audio", 0);
        String detail = "|||" + audio;
        Object video = event.getParams().get("video");
        if (video == null) {
            detail += ",0,0,0|";
        } else {
            Map<String, Object> map = (Map<String, Object>) video;
            Object detailList = map.get("detail");
            if (detailList == null) {
                detail += ",0,0,0|";
            } else {
                List<Map<String, Integer>> list = (List<Map<String, Integer>>) detailList;
                AtomicInteger time480p = new AtomicInteger();
                AtomicInteger time720p = new AtomicInteger();
                AtomicInteger time1080p = new AtomicInteger();
                list.forEach(one -> {
                    Integer height = one.get("height");
                    Integer time = one.get("time");
                    if (height <= HEIGHT_480) {
                        time480p.addAndGet(time);
                    } else if (height <= HEIGHT_720) {
                        time720p.addAndGet(time);
                    } else {
                        time1080p.addAndGet(time);
                    }
                });
                detail += "," + time480p.get() + "," + time720p.get() + "," + time1080p.get() + "|";
            }
        }
        KafkaTicketPo po = new KafkaTicketPo();
        po.setCallId((String) event.getParams().get("callId"));
        po.setDetail(detail);
        return po;
    }

}
