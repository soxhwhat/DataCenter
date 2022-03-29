package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.StatType;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdAgentOpStatPartPO;
import com.juphoon.rtc.datacenter.mapper.AcdAgentOpStatPartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * <p>坐席信息抽象类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Slf4j
public abstract class AbstractAcdAgentOpStatPartHandler extends AbstractAcdStatHandler<AcdAgentOpStatPartPO> {

    @Autowired
    private AcdAgentOpStatPartMapper acdAgentOpStatPartMapper;

    /**
     * 设置handler的统计类型
     *
     * @return
     */
    public abstract StatType statType();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handle(EventContext ec, AcdAgentOpStatPartPO po) {
        Long beginTimestamp = ec.getEvent().beginTimestamp();
        Long endTimestamp = ec.getEvent().endTimestamp();

        List<AcdAgentOpStatPartPO> list = splitStatTime(po, beginTimestamp, endTimestamp, statType());
        try {
            list.forEach(this::upsert);
        } catch (Exception e) {
            log.info("", e);
        }
    }

    @Override
    public AcdAgentOpStatPartPO poFromEvent(Event event) {
        // TODO 参数校验
        AcdAgentOpStatPartPO po = new AcdAgentOpStatPartPO();
        po.fromEvent(event);
        po.setStatType(statType().getStatType());
        return po;
    }

    @Override
    public List<EventType> careEvents() {
        // TODO 待定事件：转接，邀请坐席
        return Arrays.asList(TICKER_EVENT_RING, TICKER_EVENT_TALK, AGENT_OP_EVENT_BUSY, AGENT_OP_EVENT_FREE,
                AGENT_OP_EVENT_KEEP, AGENT_OP_EVENT_LOGIN);
    }

    @Override
    public AcdAgentOpStatPartPO selectByUnique(AcdAgentOpStatPartPO po) {
        return acdAgentOpStatPartMapper.selectByUniqueCondition(AcdAgentOpStatPartPO::getUniqueKey, po.getUniqueKey());
    }

    @Override
    public int insertSelective(AcdAgentOpStatPartPO po) {
        // TODO 加个缓存，先从缓存中查询
        return acdAgentOpStatPartMapper.insertSelective(po);
    }

    /**
     * /// todo 表名不便作为参数
     *
     * @param uniqueKey
     * @param duration
     * @param cnt
     * @return
     */
    @Override
    public int updateByUniqueKey(String uniqueKey, Long duration, Integer cnt) {
        return acdAgentOpStatPartMapper.updateAddValueByUniqueKey("jrtc_acd_agentop_stat_part", uniqueKey, duration, cnt);
    }

}
