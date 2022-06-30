package com.juphoon.rtc.datacenter.datacore.handler;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractHttpEventProcessor;
import org.springframework.web.client.RestTemplate;

/**
 * <p>HTTP事件处理handler抽象</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/21/22 3:18 PM
 */
public abstract class AbstractHttpEventHandler extends AbstractEventHandler {

    @Override
    public AbstractHttpEventProcessor getProcessor() {
        assert this.processor instanceof AbstractHttpEventProcessor : "processor 必须为HttpEventProcessor继承类";

        return (AbstractHttpEventProcessor) this.processor;
    }

    @Override
    public boolean handle(EventContext ec) throws Exception {
        return getProcessor().exchange(ec, this);
    }

    /**
     * HTTP处理回调
     *
     * @param url
     * @param restTemplate
     * @param ec
     * @return
     */
    public abstract boolean exchange(final String url, final RestTemplate restTemplate, EventContext ec);
}
