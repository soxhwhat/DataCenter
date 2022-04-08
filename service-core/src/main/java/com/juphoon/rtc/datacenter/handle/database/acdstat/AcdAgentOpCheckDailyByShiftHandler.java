package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.*;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdAgentOpCheckDailyPO;
import com.juphoon.rtc.datacenter.mapper.AcdAgentOpCheckDailyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.AGENT_OP_EVENT_CHECK_IN;
import static com.juphoon.rtc.datacenter.api.EventType.AGENT_OP_EVENT_CHECK_OUT;


/**
 * <p>jrtc_acd_agentop_check_daily表的handler类</p>
 * <p>本handler按照班次进行更新坐席的签入签出时间</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/4/6
 */
@Slf4j
@Component
public class AcdAgentOpCheckDailyByShiftHandler extends AbstractAcdStatHandler<AcdAgentOpCheckDailyPO> {
    @Autowired
    private AcdAgentOpCheckDailyMapper acdAgentOpCheckDailyMapper;

    @Override
    public StatType statType() {
        return StatType.STAT_DAY;
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdAgentOpCheckDailyByShiftHandler;
    }

    @Override
    public boolean handle(EventContext ec, AcdAgentOpCheckDailyPO po) {
        long beginTimestamp = ec.getEvent().beginTimestamp();
        long statTime = LocalDateTime.of(Instant.ofEpochMilli(beginTimestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate(), LocalTime.MIN)
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        try {
            po.setStatTime(statTime);
            upsert(po);
        } catch (Exception e) {
            log.warn("ec.id[{}], handler[{}] handle failed!", ec.getId(), handlerId().getName());
            log.warn(e.getMessage(), e);
            // 捕获了异常，需手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    /**
     * 插入或更新数据
     * // TODO 优化
     *
     * @param commonPo
     * @return
     */
    @Override
    protected void upsert(AcdAgentOpCheckDailyPO commonPo) {
        AcdAgentOpCheckDailyPO localObj = selectByUnique(commonPo);
        if (null == localObj) {
            // 上锁，保证并发时 确定只有一条数据insert
            synchronized (this) {
                localObj = selectByUnique(commonPo);
                if (null == localObj) {
                    try {
                        insertSelective(commonPo);
                    } catch (DuplicateKeyException e) {
                        tryUpdate(commonPo);
                    }
                    return;
                }
            }
        }
        if (localObj.getFirstCheckIn() == null && commonPo.getFirstCheckIn() != null) {
            updateFirstCheckInByUniqueKey(commonPo);
        } else {
            updateByUniqueKey(commonPo);
        }
    }

    @Override
    protected void tryUpdate(AcdAgentOpCheckDailyPO commonPo) {
        AcdAgentOpCheckDailyPO localObj = selectByUnique(commonPo);
        if (null != localObj) {
            if (localObj.getFirstCheckIn() == null) {
                updateFirstCheckInByUniqueKey(commonPo);
            } else {
                updateByUniqueKey(commonPo);
            }
        } else {
            log.warn("{}", commonPo.toString());
            throw new RuntimeException("insert failed, then update ,but not found data!!!");
        }
    }

    @Override
    public AcdAgentOpCheckDailyPO poFromEvent(Event event) {
        AcdAgentOpCheckDailyPO po = new AcdAgentOpCheckDailyPO();
        po.fromEvent(event);
        if (event.getEventType().getNumber().equals(AGENT_OP_EVENT_CHECK_IN.getNumber())) {
            po.setFirstCheckIn(event.beginTimestamp());
            po.setLastCheckIn(event.beginTimestamp());
        } else if (event.getEventType().getNumber().equals(AGENT_OP_EVENT_CHECK_OUT.getNumber())) {
            po.setLastCheckOut(event.beginTimestamp());
        }
        return po;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(AGENT_OP_EVENT_CHECK_IN, AGENT_OP_EVENT_CHECK_OUT);
    }

    @Override
    public AcdAgentOpCheckDailyPO selectByUnique(AcdAgentOpCheckDailyPO po) {
        return acdAgentOpCheckDailyMapper.selectByShiftAndAgentId(po.getShift(), po.getAgentId());
    }

    @Override
    public int insertSelective(AcdAgentOpCheckDailyPO po) {
        return acdAgentOpCheckDailyMapper.insertSelective(po);
    }

    @Override
    public void updateByUniqueKey(AcdAgentOpCheckDailyPO po) {
        acdAgentOpCheckDailyMapper.updateLastCheckByUniqueKey(po.getUniqueKey(), po.getLastCheckIn(), po.getLastCheckOut());
    }

    public void updateFirstCheckInByUniqueKey(AcdAgentOpCheckDailyPO po) {
        acdAgentOpCheckDailyMapper.updateFirstCheckByUniqueKey(po.getUniqueKey(), po.getFirstCheckIn(), po.getLastCheckIn());
    }
}
