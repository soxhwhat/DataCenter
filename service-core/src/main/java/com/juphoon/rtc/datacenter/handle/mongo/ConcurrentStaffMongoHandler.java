//package com.juphoon.rtc.datacenter.handle.mongo;
//
//import com.juphoon.rtc.datacenter.api.EventContext;
//import com.juphoon.rtc.datacenter.api.EventType;
//import com.juphoon.rtc.datacenter.api.HandlerId;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static com.juphoon.rtc.datacenter.api.EventType.ROOM_BEAT;
//import static com.juphoon.rtc.datacenter.api.EventType.STAFF_BEAT;
//
///**
// * 并发事件数据处理
// *
// * @author zhiwei.zhai
// * @date 2022-05-31
// */
//@Slf4j
//@Component
//public class ConcurrentStaffMongoHandler extends AbstractMongoHandler {
//
//    @Autowired
//    @Qualifier("eventMongoTemplate")
//    private MongoTemplate mongoTemplate;
//
//    @Override
//    public HandlerId handlerId() {
//        return HandlerId.ConcurrentStaffMongoHandler;
//    }
//
//    @Override
//    public List<EventType> careEvents() {
//        return Arrays.asList(
//                STAFF_BEAT
//        );
//    }
//
//    @Override
//    public String collectionName(EventContext ec) {
//        return "jrtc_staff_concurrent";
//    }
//
//    @Override
//    public MongoTemplate mongoTemplate() {
//        return mongoTemplate;
//    }
//
//}
