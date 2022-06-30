package com.juphoon.rtc.datacenter.datacore.processor.queue.impl;

import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractStateProcessor;
import com.juphoon.rtc.datacenter.datacore.processor.queue.AbstractStateQueueService;
import com.juphoon.rtc.datacenter.datacore.processor.queue.QueueServiceConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
@Slf4j
public class NoneStateQueueServiceImpl extends AbstractStateQueueService {
    public NoneStateQueueServiceImpl(AbstractStateProcessor processor, QueueServiceConfig config) {
        super(processor, config);
    }

    /**
     * 提交事件
     *
     * @param context
     * @return
     * @throws Exception
     */
    @Override
    public void onSubmit(StateContext context) {
        log.info("submit context:{},{}", context, this.getProcessor().getId());
        getProcessor().process(context);
    }

}