package com.juphoon.rtc.datacenter.event.queue.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.event.queue.AbstractEventQueueService;
import com.juphoon.rtc.datacenter.processor.AbstractEventProcessor;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>简单消息队列，不建议上生产</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/24/22 2:52 PM
 *
 */
@Slf4j
public class NoneEventQueueServiceImpl extends AbstractEventQueueService {
    public NoneEventQueueServiceImpl(AbstractEventProcessor processor) {
        super(processor);
    }

    @Override
    public void onSubmit(EventContext ec) {
        log.info("submit ec:{},{}", ec, this);
        getProcessor().onProcess(ec);
    }
}
