//package com.juphoon.rtc.datacenter.event.storage.sqllite;
//
//import com.juphoon.iron.component.utils.IronJsonUtils;
//import com.juphoon.rtc.datacenter.api.Event;
//import com.juphoon.rtc.datacenter.api.EventContext;
//import com.juphoon.rtc.datacenter.event.storage.IEventLogService;
//import com.juphoon.rtc.datacenter.service.EventService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author: Zhiwei.zhai
// * @Date: 2022/3/17 16:35
// * @Description:
// */
//@Component
//@Slf4j
//public class ScheduledService {
//
//    @Autowired
//    private IEventLogService logService;
//
//    @Autowired
//    private EventService dataService;
//
//    @Scheduled(cron = "*/5 * * * * ?")
//    public void scanRedo() {
//        List<EventPO> records = logService.getEventPageReadying().getRecords();
//        storage.info("============正在执行扫描线程====,当前重做数:{}",records.size());
//        if (CollectionUtils.isEmpty(records)) {
//            return;
//        }
//        for (EventPO eventPo : records) {
//            try {
//                EventContext eventContext = transToEventContext(eventPo);
//                if (eventContext == null) {
//                    continue;
//                }
//                dataService.commit(eventContext);
//            } catch (Exception e) {
//                storage.error("当前数据重做失败:{} , e:{}", eventPo, e);
//            }
//        }
//        storage.info("扫描线程结束");
//    }
//
//    private EventContext transToEventContext(EventPO eventPo) {
//        List<RedoPO> redoDataByEventId = logService.getRedoDataByEventId(eventPo.getId());
//        if (CollectionUtils.isEmpty(redoDataByEventId)) {
//            return null;
//        }
//        Map<String, String> redoClzMap = new HashMap<>();
//        redoDataByEventId.forEach(redoPO -> redoClzMap.put(redoPO.getHandleClz(), redoPO.getId()));
//        //封装eventContext
//        EventContext eventContext = new EventContext();
//        eventContext.setId(eventPo.getId());
//        eventContext.setRetryCount(eventPo.getRetryCount());
//        eventContext.setRedoClzMap(redoClzMap);
//        eventContext.setCreatedTimestamp(eventPo.getCreateTimestamp());
//        eventContext.setBeginTimestamp(eventPo.getBeginTimestamp());
//        eventContext.setProcessClzName(eventPo.getProcessClzName());
//        //封装event
//        Event event = new Event();
//        event.setParams(IronJsonUtils.jsonToPojo(eventPo.getParams(), Map.class));
//        event.setNumber(eventPo.getNumber());
//        event.setType(eventPo.getType());
//        eventContext.setEvent(event);
//        return eventContext;
//    }
//}
