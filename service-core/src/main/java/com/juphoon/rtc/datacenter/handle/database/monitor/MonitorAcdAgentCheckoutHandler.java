package com.juphoon.rtc.datacenter.handle.database.monitor;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorAcdAgentStatePO;
import com.juphoon.rtc.datacenter.handler.AbstractHandler;
import com.juphoon.rtc.datacenter.mapper.MonitorAcdAgentStateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.ACD_AGENT_STATE_CHECK_OUT;

/**
 * @author ajian.zheng@juphoon.com
 * @date 6/21/22 16:22
 * @description
 *
 * 坐席签出，更新监控中的签出时间戳字段，并更新为签出状态
 *
 * (
 *    domainId=100645,
 *    appId=4,
 *    uuid=a5f2be31-20a0-44bf-a062-9a3e1a19da00,
 *    type=100,
 *    number=14,
 *    timestamp=1655710419029,
 *    updateTime=1655710421908,
 *    params={
 *       duration=0,
 *       agentId=[username:agent3@100645.cloud.justalk.com],
 *       beginTimestamp=1655710419029,
 *       endType=0,
 *       appId=4,
 *       endTimestamp=1655710419029,
 *       domainId=100645
 *    }
 * )
 */
@Slf4j
@Component
public class MonitorAcdAgentCheckoutHandler extends AbstractHandler<EventContext> {

    @Autowired
    private MonitorAcdAgentStateMapper agentStateMapper;

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(EventType.AGENT_OP_EVENT_CHECK_OUT);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.MonitorAcdAgentCheckoutHandler;
    }

    @Override
    public boolean handle(EventContext context) throws Exception {
        log.debug("context:{}", context);

        MonitorAcdAgentStatePO po = MonitorAcdAgentStatePO.fromEvent(context, ACD_AGENT_STATE_CHECK_OUT, 0);

        log.debug("po:{}", po);

        // just update
        agentStateMapper.updateCheckOutByAgentId(po);

        return true;
    }
}
