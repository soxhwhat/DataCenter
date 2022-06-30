package com.juphoon.rtc.datacenter.datacore.processor;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.handler.AbstractHttpEventHandler;

/**
 * <p>HTTP事件处理器抽象类</p>
 * <p>将通用的HTTP处理流程抽象化</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/21/22 4:16 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public abstract class AbstractHttpEventProcessor extends AbstractEventProcessor {
    /**
     * 处理HTTP请求
     *
     * @param ec
     * @param handler
     * @return
     */
    public abstract boolean exchange(EventContext ec, AbstractHttpEventHandler handler);
}
