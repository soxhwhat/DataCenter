package com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>处理小时渠道平台的统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/5/5
 */
@Slf4j
@Component
public class AcdExtTerminalPartHourHandler extends AbstractAcdExtTerminalPartHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdExtTerminalPartHourHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_HOUR;
    }

}
