package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.*;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdCallInfoStatDailyPO;
import com.juphoon.rtc.datacenter.log.sqllite.mapper.AcdCallInfoStatDailyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public String getName() {
        return "话务统计日报Handler";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handle(EventContext ec, AcdCallInfoStatDailyPO po) {
        Long beginTimestamp = ec.getEvent().beginTimestamp();
        Long endTimestamp = ec.getEvent().endTimestamp();

        // 可能存在跨天
        List<AcdCallInfoStatDailyPO> list = splitStatTime(po, beginTimestamp, endTimestamp, StatType.STAT_DAY);
        try {
            list.forEach(this::upsert);
        } catch (Exception e) {
            log.info("", e);
        }
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
        return Arrays.asList(TICKER_STATUS_WAIT, TICKER_STATUS_RING, TICKER_STATUS_TALK, TICKER_STATUS_OVERFLOW);
    }

    @Override
    public AcdCallInfoStatDailyPO selectByUnique(AcdCallInfoStatDailyPO commonPo) {
        // TODO 加个缓存，先从缓存中查询
        return acdCallInfoStatDailyMapper.selectByCondition(AcdCallInfoStatDailyPO::getUniqueHashCode,
                commonPo.getUniqueHashCode());
    }

    @Override
    public int insertSelective(AcdCallInfoStatDailyPO commonPo) {
        return acdCallInfoStatDailyMapper.insertSelective((AcdCallInfoStatDailyPO) commonPo);
    }

    @Override
    public int updateByUniqueHashCode(Integer uniqueHashCode, Long duration, Integer cnt) {
        return acdCallInfoStatDailyMapper.updateAddValueByUniqueHashCode("jrtc_acd_callinfo_stat_daily", uniqueHashCode, duration, cnt);
    }


}
