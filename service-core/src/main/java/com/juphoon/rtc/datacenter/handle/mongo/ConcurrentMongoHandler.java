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

import static com.juphoon.rtc.datacenter.api.EventType.CONCURRENT_BEAT;
import static com.juphoon.rtc.datacenter.api.EventType.INFO_EVENT;
import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.UNDERLINE_TIMESTAMP;

/**
 * 并发事件数据处理
 *
 * @author zhiwei.zhai
 * @date 2022-05-31
 */
@Slf4j
@Component
public class ConcurrentMongoHandler extends AbstractMongoHandler {

    @Autowired
    @Qualifier("eventMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public HandlerId handlerId() {
        return HandlerId.ConcurrentMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                CONCURRENT_BEAT
        );
    }

    @Override
    public String collectionName(EventContext ec) {
        return "jrtc_concurrent";
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

}
