package com.juphoon.rtc.datacenter.cube.service;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/4/8 10:00
 * @Description:
 */
//@Slf4j
//@Component
//public class EventCollectionServerProcess extends AbstractServerProcess<DataCollection.Event>{

//    @Override
//    public List<EventContext> trans(List<DataCollection.Event> from, String host, String magic) throws
//            JsonProcessingException {
//        List<EventContext> ret = new ArrayList<>();
//        for (DataCollection.Event f : from) {
//            Event t = trans(f);
//            EventContext ec = new EventContext();
//            ec.setRequestId(t.getUuid());
//            ec.setEvent(t);
//            ec.setFrom(host);
////            ec.setMagic(magic);
//            ret.add(ec);
//        }
//        return ret;
//    }
//
//    @Override
//    public Event trans(DataCollection.Event from) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
//        };
//        Map<String, Object> params = mapper.readValue(from.params, typeRef);
//        return Event.builder()
//                .domainId((int) from.domainId)
//                .appId((int) from.appId)
//                .type(from.type)
//                .number(from.eventNumber)
//                .timestamp(from.timestamp)
//                .params(params)
//                .uuid(from.uuid)
//                .build();
//    }

//}
