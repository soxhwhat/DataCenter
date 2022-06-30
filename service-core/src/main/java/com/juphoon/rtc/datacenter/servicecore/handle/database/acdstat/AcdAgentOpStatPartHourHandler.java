package com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>处理小时话务统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @since 2022/3/24
 */
@Slf4j
@Component
public class AcdAgentOpStatPartHourHandler extends AbstractAcdAgentOpStatPartHandler {

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdAgentOpStatPartHourHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_HOUR;
    }
}
