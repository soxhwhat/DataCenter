package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 10:39 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
public class TestProcessor extends AbstractEventProcessor {
    @Override
    ProcessorId processorId() {
        return ProcessorId.TEST;
    }

    @Value(value = "${demo.value:hi}")
    private String value;

    public TestProcessor() {
    }

    @Override
    public void process(EventContext ec) {
        log.info("process:{},{}", value, this);

        getEventHandlers().forEach(h -> {
            try {
                h.handle(ec);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
