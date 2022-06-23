package com.juphoon.rtc.datacenter.handle.database.monitor;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorAcdQueueCountPO;
import com.juphoon.rtc.datacenter.handler.AbstractHandler;
import com.juphoon.rtc.datacenter.mapper.MonitorAcdQueueCountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * <p>视频客服排队机 队列监控</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 6/21/22 11:10 AM
 * @description
 *
 * FlowStatusJson(
 *uniqueId = 7000,
 *type = 13,
 *status = 0,
 *params = {
 *    "from" : "#CcAcd@CcAcd.Main2-0.Main",
 *    "queue" : "7000",
 *    "timestamp" : 1655263216229,
 *    "waitCount" : 0
 *  },
 *  domainId=103296,
 *  appId=1),
 * FlowStatusJson(
 *  uniqueId=10000,
 *  type=13,
 *  status=0,
 *  params={
 *    "from" : "#CcAcd@CcAcd.Main2-0.Main",
 *    "queue" : "10000",
 *    "timestamp" : 1655263216229,
 *    "waitCount" : 0
 *  },
 *  domainId=100645,
 *  appId=4), FlowStatusJson(uniqueId=10091, type=13, status=0, params={
 *    "from" : "#CcAcd@CcAcd.Main2-0.Main",
 *    "queue" : "10091",
 *    "timestamp" : 1655263216229,
 *    "waitCount" : 0
 * }.......
 */
@Slf4j
@Component
public class MonitorAcdQueueCountHandler extends AbstractHandler<StateContext> {

    @Autowired
    private MonitorAcdQueueCountMapper monitorAcdQueueMapper;

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.QUEUE_WAIT_BEAT, EventType.QUEUE_RING_BEAT, EventType.QUEUE_CALL_BEAT);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.MonitorAcdQueueCountHandler;
    }

    @Override
    public boolean handle(StateContext context) throws Exception {
        log.debug("context:{}", context);

        MonitorAcdQueueCountPO po = MonitorAcdQueueCountPO.fromState(context);

        log.debug("po:{}", po);

        // update or insert
        int ret = monitorAcdQueueMapper.updateByQueueAndFrom(po);
        if (0 == ret) {
            monitorAcdQueueMapper.insert(po);
        }

        return true;
    }


}