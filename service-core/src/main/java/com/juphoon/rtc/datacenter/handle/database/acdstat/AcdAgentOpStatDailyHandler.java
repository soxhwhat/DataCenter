package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.*;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdAgentOpStatDailyPO;
import com.juphoon.rtc.datacenter.mapper.AcdAgentOpStatDailyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.*;


/**
 * <p>jrtc_acd_agentop_stat_daily表的handler类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
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
    public HandlerId handlerId() {
        return HandlerId.AcdAgentOpStatDailyHandler;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handle(EventContext ec, AcdAgentOpStatDailyPO po) {
        Long beginTimestamp = ec.getEvent().beginTimestamp();
        Long endTimestamp = ec.getEvent().endTimestamp();

        // 可能存在跨天
        List<AcdAgentOpStatDailyPO> list = splitStatTime(po, beginTimestamp, endTimestamp, StatType.STAT_DAY);
        try {
            list.forEach(this::upsert);
        } catch (Exception e) {
            log.info("", e);
        }
    }

    @Override
    public AcdAgentOpStatDailyPO poFromEvent(Event event) {
        // TODO 参数校验
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
    public AcdAgentOpStatDailyPO selectByUnique(AcdAgentOpStatDailyPO commonPo) {
        // TODO 加个缓存，先从缓存中查询
        return acdAgentOpStatDailyMapper.selectByCondition(AcdAgentOpStatDailyPO::getUniqueKey, commonPo.getUniqueKey());
    }

    @Override
    public int insertSelective(AcdAgentOpStatDailyPO commonPo) {
        return acdAgentOpStatDailyMapper.insertSelective(commonPo);
    }

    @Override
    public int updateByUniqueKey(String uniqueKey, Long duration, Integer cnt) {
        return acdAgentOpStatDailyMapper.updateAddValueByUniqueKey("jrtc_acd_agentop_stat_daily", uniqueKey, duration, cnt);
    }
}
