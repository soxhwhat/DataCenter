package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.StatType;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdCallInfoStatPartPO;
import com.juphoon.rtc.datacenter.mapper.AcdCallInfoStatPartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.*;

/**
 * <p>话务信息抽象类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/23/22 7:35 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public abstract class AbstractAcdCallInfoStatPartHandler extends AbstractAcdStatHandler<AcdCallInfoStatPartPO> {

    @Autowired
    private AcdCallInfoStatPartMapper AcdCallInfoStatPartMapper;

    /**
     * 设置handler的统计类型
     *
     * @return
     */
    public abstract StatType statType();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handle(EventContext ec, AcdCallInfoStatPartPO po) {
        Long beginTimestamp = ec.getEvent().beginTimestamp();
        Long endTimestamp = ec.getEvent().endTimestamp();

        List<AcdCallInfoStatPartPO> list = splitStatTime(po, beginTimestamp, endTimestamp, statType());
        try {
            list.forEach(this::upsert);
        } catch (Exception e) {
            log.info("", e);
        }
    }

    @Override
    public AcdCallInfoStatPartPO poFromEvent(Event event) {
        AcdCallInfoStatPartPO po = new AcdCallInfoStatPartPO();
        po.fromEvent(event);
        po.setStatType(statType().getStatType());
        return po;
    }

    @Override
    public List<EventType> careEvents() {
        // TODO 待定事件：转接，邀请坐席
        return Arrays.asList(TICKER_STATUS_WAIT, TICKER_STATUS_RING, TICKER_STATUS_TALK, TICKER_STATUS_OVERFLOW);
    }

    @Override
    public AcdCallInfoStatPartPO selectByUnique(AcdCallInfoStatPartPO po) {
        return AcdCallInfoStatPartMapper.selectByUniqueCondition(AcdCallInfoStatPartPO::getUniqueHashCode,
                po.getUniqueHashCode());
    }

    @Override
    public int insertSelective(AcdCallInfoStatPartPO po) {
        // TODO 加个缓存，先从缓存中查询
        return AcdCallInfoStatPartMapper.insertSelective(po);
    }

    // todo 表名不便作为参数
    @Override
    public int updateByUniqueHashCode(Integer uniqueHashCode, Long duration, Integer cnt) {
        return AcdCallInfoStatPartMapper.updateAddValueByUniqueHashCode("jrtc_acd_callinfo_stat_part", uniqueHashCode, duration, cnt);
    }

}
