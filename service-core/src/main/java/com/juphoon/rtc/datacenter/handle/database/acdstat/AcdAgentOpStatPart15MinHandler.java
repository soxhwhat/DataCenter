package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.StatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>处理15分钟话务统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @since * @since 2022/3/24
 */
@Slf4j
@Component
public class AcdAgentOpStatPart15MinHandler extends AbstractAcdAgentOpStatPartHandler {

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdAgentOpStatPart15MinHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_15MIN;
    }
}
