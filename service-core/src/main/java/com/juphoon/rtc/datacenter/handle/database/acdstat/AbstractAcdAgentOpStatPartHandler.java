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
    public boolean handle(EventContext ec, AcdAgentOpStatPartPO po) {
        long beginTimestamp = ec.getEvent().beginTimestamp();
        long endTimestamp = ec.getEvent().endTimestamp();
        List<AcdAgentOpStatPartPO> list = splitStatTime(po, beginTimestamp, endTimestamp, statType());
        try {
            list.forEach(this::upsert);
        } catch (Exception e) {
            log.warn("ec.id[{}], handler[{}] handle failed!", ec.getId(), handlerId().getName());
            log.warn(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public AcdAgentOpStatPartPO poFromEvent(Event event) {
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
        return acdAgentOpStatPartMapper.selectByUniqueKey(po.getUniqueKey());
    }

    @Override
    public int insertSelective(AcdAgentOpStatPartPO po) {
        // TODO 加个缓存，先从缓存中查询
        return acdAgentOpStatPartMapper.insertSelective(po);
    }

    /***
     *
     * @param po
     * @return
     */
    @Override
    public int updateByUniqueKey(AcdAgentOpStatPartPO po) {
        return acdAgentOpStatPartMapper.updateAddValueByUniqueKey(po.getUniqueKey(), po.getDuration(), po.getCnt());
    }

}
