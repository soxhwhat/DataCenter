package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.StatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>处理15分钟话务统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @since 2022/3/10 11:49
 * @update
 * <p>1. 2022-03-23. ajian.zheng 抽象后的15分钟handler</p>
 */
@Slf4j
@Component
public class AcdCallInfoStatPart15MinHandler extends AbstractAcdCallInfoStatPartHandler {
    @Override
    public String getName() {
        return "话务统计15分钟Handler";
    }

    @Override
    public StatType statType() {
        return StatType.STAT_15MIN;
    }
}
