package com.juphoon.rtc.datacenter.handler.inner;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.handler.AbstractCareAllEventHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <p>通用第一个处理句柄</p>
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
@Setter
@Scope("prototype")
public class FirstInnerEventHandler extends AbstractCareAllEventHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.FIRST;
    }

    @Override
    public String getName() {
        return getProcessor().getName() + handlerId().getName();
    }

    @Override
    public String getId() {
        return getProcessor().getId() + handlerId().getId();
    }

    @Override
    public boolean handle(EventContext ec) {
        log.debug("handle ec:{}", ec.getRequestId());

        ec.handle();
        return true;
    }
}
