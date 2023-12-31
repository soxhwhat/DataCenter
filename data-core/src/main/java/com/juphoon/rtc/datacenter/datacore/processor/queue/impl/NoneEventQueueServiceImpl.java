package com.juphoon.rtc.datacenter.datacore.processor.queue.impl;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractEventProcessor;
import com.juphoon.rtc.datacenter.datacore.processor.queue.AbstractEventQueueService;
import com.juphoon.rtc.datacenter.datacore.processor.queue.QueueServiceConfig;
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
    public NoneEventQueueServiceImpl(AbstractEventProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }

    @Override
    public void onSubmit(EventContext context) {
        log.info("submit context:{},{}", context, this.getProcessor().getId());
        getProcessor().process(context);
    }
}
