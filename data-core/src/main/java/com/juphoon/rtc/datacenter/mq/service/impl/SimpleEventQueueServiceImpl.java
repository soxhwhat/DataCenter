package com.juphoon.rtc.datacenter.mq.service.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.mq.service.AbstractEventQueueService;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>简单消息队列，不建议上生产</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/24/22 2:52 PM
 */
@Slf4j
//@Component
//@ConditionalOnProperty(prefix = JrtcDataCenterConstant.DATA_CENTER_CONFIG_PREFIX, name = "queue", havingValue = "simple")
public class SimpleEventQueueServiceImpl extends AbstractEventQueueService {
    @Override
    public void submit(EventContext ec) {
        log.info("submit ec:{}", ec);
        getProcessor().onProcess(ec);
    }
}
