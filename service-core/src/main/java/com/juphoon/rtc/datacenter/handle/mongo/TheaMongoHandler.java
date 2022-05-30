//package com.juphoon.rtc.datacenter.handle.mongo;
//
//import com.juphoon.rtc.datacenter.api.EventContext;
//import com.juphoon.rtc.datacenter.api.EventType;
//import com.juphoon.rtc.datacenter.api.HandlerId;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.index.Index;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.ConcurrentSkipListSet;
//
//import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.*;
//
//
///**
// * @Author: Zhiwei.zhai
// * @Date: 2022/3/22 14:58
// * @Description:
// */
//@Component
//@Slf4j
//public class TheaMongoHandler extends AbstractMongoHandler {
//    @Override
//    public HandlerId handlerId() {
//        return HandlerId.TheaMongoHandler;
//    }
//
//    public static final String TESSAR = "tessar";
//    public static final String TYPE = ".type";
//    public static final String EVENTNUMBER = ".eventNumber";
//    public static final String THEA_CLIENT = "thea_client_";
//    public static final String THEA_SERVER = "thea_server_";
//
//
//    @Override
//    public List<EventType> careEvents() {
//        return Arrays.asList(EventType.TICKER_EVENT_WAIT, EventType.TICKER_EVENT_RING
//                , EventType.TICKER_EVENT_TALK, EventType.TICKER_EVENT_OVERFLOW
//                , EventType.TICKER_EVENT_TRANSFER, EventType.TICKER_EVENT_INVITE_AGENT);
//    }
//
//    @Override
//    public boolean handle(EventContext ec) throws Exception {
////        Map<String, Object> params = ec.getEvent().getParams();
////        JSONObject jsonObject = (JSONObject) JSON.toJSON(params);
////        Event eventPo = jsonObject.toJavaObject(Event.class);
////        // 900  client 901 server
////        JSONObject theaClientJson = new JSONObject();
////        if (eventPo.getParams().contains(TESSAR) && eventPo.getType() == THEA_CLIENT_CODE) {
////            theaClientJson.put("value", eventPo.getParams());
////            theaClientJson.put("thea_client_timestamp", System.currentTimeMillis());
////            getMongoTemplate().insert(theaClientJson.toString(), THEA_CLIENT_COLLECTION);
////            return true;
////        }
////        JSONObject theaServerJson = new JSONObject();
////        if (eventPo.getParams().contains(TESSAR) && eventPo.getType() == THEA_SERVER_CODE) {
////            theaServerJson.put("value", eventPo.getParams());
////            theaServerJson.put("thea_server_timestamp", System.currentTimeMillis());
////            getMongoTemplate().insert(theaServerJson.toString(), THEA_SERVER_COLLECTION);
////            return true;
////        }
//        return true;
//    }
//
//
//    //    @PostConstruct
//    public void init() {
//        Set<String> indexKeyMap = new ConcurrentSkipListSet<>();
//        getMongoTemplate().indexOps(EVENT_COLLECTION).getIndexInfo().forEach(v -> {
//            v.getIndexFields().forEach(index -> {
//                indexKeyMap.add(index.getKey());
//            });
//        });
//        getMongoTemplate().indexOps(THEA_CLIENT_COLLECTION).getIndexInfo().forEach(v -> {
//            v.getIndexFields().forEach(index -> {
//                indexKeyMap.add(index.getKey());
//            });
//        });
//        getMongoTemplate().indexOps(THEA_SERVER_COLLECTION).getIndexInfo().forEach(v -> {
//            v.getIndexFields().forEach(index -> {
//                indexKeyMap.add(index.getKey());
//            });
//        });
//
//        if (!indexKeyMap.contains(UNDER_LINE + TIMESTAMP)) {
//            createIndex(EVENT_COLLECTION, "_" + TIMESTAMP);
//        }
//
//        if (!indexKeyMap.contains(VERSION_SOURCE + POINT + TAGS)) {
//            createIndex(EVENT_COLLECTION, VERSION_SOURCE + "." + TAGS);
//        }
//        if (!indexKeyMap.contains(VERSION_SOURCE + TYPE)) {
//            createIndex(EVENT_COLLECTION, VERSION_SOURCE + ".type");
//        }
//        if (!indexKeyMap.contains(VERSION_SOURCE + EVENTNUMBER)) {
//            createIndex(EVENT_COLLECTION, VERSION_SOURCE + ".eventNumber");
//        }
//        if (!indexKeyMap.contains(THEA_CLIENT + TIMESTAMP)) {
//            createIndex(THEA_CLIENT_COLLECTION, "thea_client_" + TIMESTAMP);
//        }
//        if (!indexKeyMap.contains(THEA_SERVER + TIMESTAMP)) {
//            createIndex(THEA_SERVER_COLLECTION, "thea_server_" + TIMESTAMP);
//        }
//    }
//
//    private void createIndex(String collectionName, String key) {
//        Index keyIndex = new Index();
//        keyIndex.on(key, Sort.Direction.ASC);
//        getMongoTemplate().indexOps(collectionName).ensureIndex(keyIndex);
//    }
//}
