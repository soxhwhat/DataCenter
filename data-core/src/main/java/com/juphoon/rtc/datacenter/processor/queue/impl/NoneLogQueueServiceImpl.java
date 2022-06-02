package com.juphoon.rtc.datacenter.processor.queue.impl;

import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.processor.AbstractLogProcessor;
import com.juphoon.rtc.datacenter.processor.queue.AbstractLogQueueService;
import com.juphoon.rtc.datacenter.processor.queue.QueueServiceConfig;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>简单消息队列，不建议上生产</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/24/22 2:52 PM
 *
 */
@Slf4j
public class NoneLogQueueServiceImpl extends AbstractLogQueueService {
    public NoneLogQueueServiceImpl(AbstractLogProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }

    @Override
    public void onSubmit(LogContext context) {
        log.info("submit context:{},{}", context, this.getProcessor().getId());
        getProcessor().process(context);
    }
}
