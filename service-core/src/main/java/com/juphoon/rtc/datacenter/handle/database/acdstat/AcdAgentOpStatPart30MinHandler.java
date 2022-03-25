package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.StatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>处理30分钟话务统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @since 2022/3/24
 */
@Slf4j
@Component
public class AcdAgentOpStatPart30MinHandler extends AbstractAcdAgentOpStatPartHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdAgentOpStatPart30MinHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_30MIN;
    }
}
