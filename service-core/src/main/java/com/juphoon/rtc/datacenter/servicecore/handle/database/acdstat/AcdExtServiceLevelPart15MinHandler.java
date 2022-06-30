package com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>处理15分钟20s内接通量的统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/4/6
 */

@Slf4j
@Component
public class AcdExtServiceLevelPart15MinHandler extends AbstractAcdExtServiceLevelPartHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdExtServiceLevelPart15MinHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_15MIN;
    }

}
