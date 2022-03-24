package com.juphoon.rtc.datacenter.handler.inner;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.handler.AbstractCareAllEventHandler;
import com.juphoon.rtc.datacenter.log.IEventLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <p>通用最后处理句柄</p>
 * // 最后一个句柄需要处理
 * // 1. 判断是否已经完成处理，若已经完成，则从mq中确认（删除）
 * // 2. 日志等
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 5:06 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
@Scope("prototype")
public class LastInnerEventHandler extends AbstractCareAllEventHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.LAST;
    }
    
    @Autowired
    private IEventLogService eventLogService;

    @Override
    public boolean handle(EventContext ec) {
        if (ec.processOk()) {
            eventLogService.removeEvent(ec);
        }

        log.info("{} process ec:{},{} ret:{},cost:{}",
                ec.getProcessorId(),
                ec.getEvent().eventType(),
                ec.getEvent().eventNumber(),
                ec.processOk(),
                System.currentTimeMillis() - ec.getBeginTimestamp()
        );

        return true;
    }

    @Override
    public String getId() {
        return this.getClass().getName();
    }
}
