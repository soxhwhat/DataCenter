package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.StatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>处理30分钟渠道平台的统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/5/5
 */
@Slf4j
@Component
public class AcdExtTerminalPart30MinHandler extends AbstractAcdExtTerminalPartHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdExtTerminalPart30MinHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_30MIN;
    }

}