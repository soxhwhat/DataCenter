package com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat.AcdAgentOpStatDailyPO;
import com.juphoon.rtc.datacenter.servicecore.mapper.AcdAgentOpStatDailyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.*;

/**
 * <p>jrtc_acd_agentop_stat_daily表的handler类</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Slf4j
@Component
public class AcdAgentOpStatDailyHandler extends AbstractAcdStatHandler<AcdAgentOpStatDailyPO> {
    @Autowired
    private AcdAgentOpStatDailyMapper acdAgentOpStatDailyMapper;

    @Override
    public StatType statType() {
        return StatType.STAT_DAY;
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdAgentOpStatDailyHandler;
    }

    @Override
    public AcdAgentOpStatDailyPO poFromEvent(Event event) {
        AcdAgentOpStatDailyPO po = new AcdAgentOpStatDailyPO();
        po.fromEvent(event);
        return po;
    }

    @Override
    public List<EventType> careEvents() {
        // TODO 待定事件：转接，邀请坐席
        return Arrays.asList(TICKER_EVENT_RING, TICKER_EVENT_TALK, AGENT_OP_EVENT_BUSY, AGENT_OP_EVENT_FREE,
                AGENT_OP_EVENT_KEEP, AGENT_OP_EVENT_LOGIN);
    }

    @Override
    public AcdAgentOpStatDailyPO selectByUnique(AcdAgentOpStatDailyPO po) {
        return acdAgentOpStatDailyMapper.selectByUniqueKey(po.getUniqueKey());
    }

    @Override
    public int insertSelective(AcdAgentOpStatDailyPO po) {
        return acdAgentOpStatDailyMapper.insertSelective(po);
    }

    @Override
    public void updateByUniqueKey(AcdAgentOpStatDailyPO po) {
        acdAgentOpStatDailyMapper.updateAddValueByUniqueKey(po.getUniqueKey(), po.getDuration(), po.getCnt());
    }
}
