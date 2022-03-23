package com.juphoon.rtc.datacenter.handler.inner;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.mq.IEventQueueService;
import com.juphoon.rtc.datacenter.handler.AbstractCareAllEventHandler;
import lombok.extern.slf4j.Slf4j;

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
public class LastInnerEventHandler extends AbstractCareAllEventHandler {

    private IEventQueueService queueService;

    public LastInnerEventHandler(IEventQueueService queueService){
        this.queueService = queueService;
    }

    @Override
    public boolean handle(EventContext ec) {
        // TODO
        log.info("LastInnerEventHandler正在执行中,ec:{}", ec);
        queueService.processOk(ec);
        return true;
    }

    @Override
    public boolean isRedo() {
        return false;
    }
}