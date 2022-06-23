package com.juphoon.rtc.datacenter.handle.database.monitor;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorAcdAgentStatePO;
import com.juphoon.rtc.datacenter.handler.AbstractHandler;
import com.juphoon.rtc.datacenter.mapper.MonitorAcdAgentStateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author ajian.zheng@juphoon.com
 * @date 6/21/22 16:22
 * @description
 *
 * 状态数据上报:FlowStatusJson(
 *    uniqueId=[username:10637048@103291.cloud.justalk.com],
 *    type=12,
 *    status=0,
 *    params={
 *       "acdId" : "#CcOm@CcOm.Main2-0.Main",
 *       "agentId" : "[username:10637048@103291.cloud.justalk.com]",
 *       "agentStatus" : 1,
 *       "checkInTimestamp" : 1655254469663,
 *       "updateTimestamp" : 1655263195948
 *    },
 *    domainId=103291,
 *    appId=0
 * )
 */
@Slf4j
@Component
public class MonitorAcdAgentStateHandler extends AbstractHandler<StateContext> {

    @Autowired
    private MonitorAcdAgentStateMapper agentStateMapper;

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(EventType.STAFF_BEAT);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.MonitorAcdAgentStateHandler;
    }

    @Override
    public boolean handle(StateContext context) throws Exception {
        log.debug("context:{}", context);

        MonitorAcdAgentStatePO po = MonitorAcdAgentStatePO.fromState(context);

        log.debug("po:{}", po);

        // update or insert
        int ret = agentStateMapper.updateStateByAgentId(po);
        if (0 == ret) {
            agentStateMapper.insert(po);
        }

        return true;
    }
}
