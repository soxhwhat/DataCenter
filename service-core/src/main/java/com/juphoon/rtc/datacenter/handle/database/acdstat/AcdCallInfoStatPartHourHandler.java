package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.StatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>处理小时话务统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @since 2022/3/10 11:49
 * @update
 * <p>1. 2022-03-23. ajian.zheng 抽象后的小时handler</p>
 */
@Slf4j
@Component
public class AcdCallInfoStatPartHourHandler extends AbstractAcdCallInfoStatPartHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdCallInfoStatPartHourHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_HOUR;
    }
}
