package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.*;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdCallInfoStatDailyPO;
import com.juphoon.rtc.datacenter.mapper.AcdCallInfoStatDailyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * <p>jrtc_acd_callinfo_stat_daily表的handler类</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @since 2022/3/10 11:49
 */
@Slf4j
@Component
public class AcdCallInfoStatDailyHandler extends AbstractAcdStatHandler<AcdCallInfoStatDailyPO> {

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdCallInfoStatDailyHandler;
    }

    @Autowired
    private AcdCallInfoStatDailyMapper acdCallInfoStatDailyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handle(EventContext ec, AcdCallInfoStatDailyPO po) {
        long beginTimestamp = ec.getEvent().beginTimestamp();
        long endTimestamp = ec.getEvent().endTimestamp();
        // 可能存在跨天
        List<AcdCallInfoStatDailyPO> list = splitStatTime(po, beginTimestamp, endTimestamp, StatType.STAT_DAY);
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
    public AcdCallInfoStatDailyPO poFromEvent(Event event) {
        AcdCallInfoStatDailyPO po = new AcdCallInfoStatDailyPO();
        po.fromEvent(event);
        return po;
    }

    @Override
    public List<EventType> careEvents() {
        // TODO 待定事件：转接，邀请坐席
        return Arrays.asList(TICKER_EVENT_WAIT, TICKER_EVENT_RING, TICKER_EVENT_TALK, TICKER_EVENT_OVERFLOW);
    }

    @Override
    public AcdCallInfoStatDailyPO selectByUnique(AcdCallInfoStatDailyPO po) {
        // TODO 加个缓存，先从缓存中查询
        return acdCallInfoStatDailyMapper.selectByUniqueKey(po.getUniqueKey());
    }

    @Override
    public int insertSelective(@Validated AcdCallInfoStatDailyPO commonPo) {
        return acdCallInfoStatDailyMapper.insertSelective(commonPo);
    }

    @Override
    public int updateByUniqueKey(AcdCallInfoStatDailyPO po) {
        return acdCallInfoStatDailyMapper.updateAddValueByUniqueKey(po.getUniqueKey(), po.getDuration(), po.getCnt());
    }


}
