//package com.juphoon.rtc.datacenter.log.sqllite;
//
//import com.alibaba.druid.support.json.JSONUtils;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.juphoon.rtc.datacenter.api.EventContext;
//import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
//import com.juphoon.rtc.datacenter.log.IEventLogService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * @Author: Zhiwei.zhai
// * @Date: 2022/3/16 10:15
// * @Description:
// */
//@Service
//public class MqLogServiceImpl implements IEventLogService {
//
//    @Autowired
//    IEventMapper eventMapper;
//
//    @Autowired
//    IRedoMapper redoMapper;
//
//
//    @Override
//    public synchronized void eventBeginLog(EventContext ec, boolean pushSuccess) {
//        EventPO eventPO = new EventPO();
//        eventPO.setId(ec.getId());
//        eventPO.setParams(JSONUtils.toJSONString(ec.getEvent().getParams()));
//        eventPO.setType(ec.getEvent().getType());
//        eventPO.setNumber(ec.getEvent().getNumber());
//        eventPO.setCreateTimestamp(System.currentTimeMillis());
//        eventPO.setProcessClzName(ec.getProcessClzName());
//        eventPO.setRetryCount(ec.getRetryCount());
//        eventPO.setState(pushSuccess ? JrtcDataCenterConstant.STATE_LOADING : JrtcDataCenterConstant.STATE_READYING);
//        eventMapper.insert(eventPO);
//    }
//
//    @Override
//    public synchronized String handleFailLog(EventContext ec, String handleClz) {
//        RedoPO redoPO = new RedoPO();
//        redoPO.setHandleClz(handleClz);
//        redoPO.setBeginTimeStamp(ec.getBeginTimestamp());
//        redoPO.setEventId(ec.getId());
//        redoPO.setRetryCount(ec.getRetryCount());
//        redoMapper.insert(redoPO);
//        return redoPO.getId();
//    }
//
//    @Override
//    public synchronized void redoSuccess(String redoId) {
//        redoMapper.deleteById(redoId);
//    }
//
//    @Override
//    public synchronized void allSuccess(String eventId) {
//        eventMapper.deleteById(eventId);
//    }
//
//    @Override
//    public synchronized void updateEventReady(String eventId) {
//        eventMapper.updateEventReady(eventId);
//    }
//
//    @Override
//    public IPage<EventPO> getEventPageReadying() {
//        Page page = new Page();
//        page.setSize(10);
//        page.setPages(0);
//        LambdaQueryWrapper<EventPO> queryWrapper = new QueryWrapper<EventPO>().lambda();
//        queryWrapper.eq(EventPO::getState, JrtcDataCenterConstant.STATE_READYING);
//        return eventMapper.selectPage(page, queryWrapper);
//    }
//
//    @Override
//    public List<RedoPO> getRedoDataByEventId(String eventId) {
//        return redoMapper.selectByEventId(eventId);
//    }
//
//    @Override
//    public void updateEventRetry(EventContext ec) {
//        EventPO eventPO = new EventPO();
//        eventPO.setId(ec.getId());
//        eventPO.setBeginTimestamp(ec.getBeginTimestamp());
//        eventPO.setRetryCount(ec.getRetryCount());
//        eventMapper.updateById(eventPO);
//    }
//
//    @Override
//    public void initTables() {
//        eventMapper.initEventTable();
//        redoMapper.initHandleDataTable();
//    }
//}
