package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.StatType;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdCallInfoStatDailyPO;
import com.juphoon.rtc.datacenter.mapper.AcdCallInfoStatDailyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    public StatType statType() {
        return StatType.STAT_DAY;
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdCallInfoStatDailyHandler;
    }

    @Autowired
    private AcdCallInfoStatDailyMapper acdCallInfoStatDailyMapper;

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
        return acdCallInfoStatDailyMapper.selectByUniqueKey(po.getUniqueKey());
    }

    @Override
    public int insertSelective(@Validated AcdCallInfoStatDailyPO commonPo) {
        return acdCallInfoStatDailyMapper.insertSelective(commonPo);
    }

    @Override
    public void updateByUniqueKey(AcdCallInfoStatDailyPO po) {
        acdCallInfoStatDailyMapper.updateAddValueByUniqueKey(po.getUniqueKey(), po.getDuration(), po.getCnt());
    }


}
