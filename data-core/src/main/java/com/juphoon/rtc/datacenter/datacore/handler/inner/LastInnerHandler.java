package com.juphoon.rtc.datacenter.datacore.handler.inner;

import com.juphoon.rtc.datacenter.datacore.api.BaseContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.handler.AbstractHandler;
import com.juphoon.rtc.datacenter.datacore.processor.IProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

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
public class LastInnerHandler<T extends BaseContext> extends AbstractHandler<T> {
    public LastInnerHandler(IProcessor<T> processor) {
        setProcessor(processor);
    }

    @Override
    public String getName() {
        return getProcessor().getId() + "_" + handlerId().getName();
    }

    @Override
    public String getId() {
        return getProcessor().getId() + handlerId().getId();
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.LAST;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.values());
    }

    @Override
    public boolean handle(T t) {
        log.debug("t:{}", t);

        /*
         * 若处理成功，则删除事件
         * 重做事件独立处理
         */
        // 顺序保证，先删库
        getProcessor().logService().remove(t);

        // 再清理set
        getProcessor().queueService().success(t);

        log.debug("{} process t:{},{} cost:{}",
                t.getProcessorId(),
                t.getEventType(),
                t,
                System.currentTimeMillis() - t.getBeginTimestamp()
        );

        return true;
    }
}
