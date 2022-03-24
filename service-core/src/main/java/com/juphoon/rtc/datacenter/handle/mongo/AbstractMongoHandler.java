package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.processor.MongoProcessor;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.UNDER_LINE;


/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:37
 * @Description:
 */
public abstract class AbstractMongoHandler extends AbstractEventHandler {

    public MongoProcessor mongoProcessor;

    public MongoProcessor getMongoProcessor() {
        if (mongoProcessor == null) {
            mongoProcessor = (MongoProcessor) processor;
        }
        return mongoProcessor;
    }

    public MongoTemplate getMongoTemplate() {
        return getMongoProcessor().getMongoTemplate();
    }

    public String getCollectionName(EventContext ec) {
        Integer type = ec.getEvent().getEventType().getType();
        String day = DateFormatUtils.format(new Date(ec.getCreatedTimestamp()), "yyyyMMdd");
        if (type.equals(EventType.TICKER_STATUS_WAIT.getType())){
            return "jrtc.events" + UNDER_LINE + day;
        } else if (type.equals(EventType.TICKER_COMPLETE)){
            return "jrtc.records" + UNDER_LINE + day;
        } else if (type.equals(EventType.STAFF_BEAT)){
            return "jrtc.status" + UNDER_LINE + day;
        } else if (type.equals(EventType.QUEUE_BEAT)){
            return "jrtc.status" + UNDER_LINE + day;
        }
        return null;
    }

}
