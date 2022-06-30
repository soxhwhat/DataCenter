package com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>处理30分钟话务统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @since 2022/3/10 11:49
 * @update
 * <p>1. 2022-03-23. ajian.zheng 抽象后的 30 分钟handler</p>
 */
@Slf4j
@Component
public class AcdCallInfoStatPart30MinHandler extends AbstractAcdCallInfoStatPartHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdCallInfoStatPart30MinHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_30MIN;
    }
}
