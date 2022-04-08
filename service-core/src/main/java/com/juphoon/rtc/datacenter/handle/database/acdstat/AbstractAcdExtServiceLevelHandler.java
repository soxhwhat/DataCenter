package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.StatType;
import com.juphoon.rtc.datacenter.entity.ServiceLevelTypeEnum;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdExtServiceLevelPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.TICKER_EVENT_RING;

/**
 * <p>jrtc_acd_ext_connect_xxxx表的抽象handler类</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/4/6
 */
@Slf4j
@Component
public abstract class AbstractAcdExtServiceLevelHandler extends AbstractAcdStatHandler<AcdExtServiceLevelPO> {

    /**
     * 多少时间内接通量
     */
    private List<ServiceLevelTypeEnum> serviceLevelTypeEnums;

    public void setServiceLevelTypeEnums(List<ServiceLevelTypeEnum> serviceLevelTypeEnums) {
        this.serviceLevelTypeEnums = serviceLevelTypeEnums;
    }

    public List<ServiceLevelTypeEnum> getServiceLevelTypeEnums() {
        return serviceLevelTypeEnums;
    }

    @Override
    public boolean handle(EventContext ec, AcdExtServiceLevelPO po) {
        long incomingTimestamp = ec.getEvent().incomingTimestamp();
        long beginTimestamp = ec.getEvent().beginTimestamp();
        long endTimestamp = ec.getEvent().endTimestamp();
        long statTime;
        if (statType().equals(StatType.STAT_DAY)) {
            statTime = LocalDateTime.of(Instant.ofEpochMilli(beginTimestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate(), LocalTime.MIN)
                    .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        } else {
            statTime = beginTimestamp - beginTimestamp % statType().getInterval();
        }
        po.setStatTime(statTime);
        po = inTimeCompare(po,incomingTimestamp ,endTimestamp, getServiceLevelTypeEnums());
        try {
            if (null != po) {
                upsert(po);
            }
        } catch (Exception e) {
            log.warn("ec.id[{}], handler[{}] handle failed!", ec.getId(), handlerId().getName());
            log.warn(e.getMessage(), e);
            // 捕获了异常，需手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    private AcdExtServiceLevelPO inTimeCompare(AcdExtServiceLevelPO po, Long incomingTimestamp, Long endTimestamp,
                                                     List<ServiceLevelTypeEnum> serviceLevelTypeEnums) {
        for (ServiceLevelTypeEnum serviceLevelTypeEnum : serviceLevelTypeEnums) {
            if (endTimestamp - incomingTimestamp <= serviceLevelTypeEnum.getInTime()) {
                po.setServiceLevel(serviceLevelTypeEnum.getServiceLevel());
                return po;
            }
        }
        return null;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(TICKER_EVENT_RING);
    }

}
