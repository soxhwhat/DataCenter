package com.juphoon.rtc.datacenter.dist.c09.handle.ticket;


import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import com.juphoon.rtc.datacenter.dist.c09.handle.push.AbstractExternalCallPushPartHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>宁波外呼push事件处理handler(1hour)</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 用于统计宁波银行客服话单通话成功率、等待时长、通话时长(1hour)
 */
@Slf4j
@Component
@Setter
public class ExternalCallTicketPartHourHandler extends AbstractExternalCallTicketPartHandler {

    @Override
    public HandlerId handlerId() {
        return HandlerId.ExternalCallTicketPartHourHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_HOUR;
    }

}
