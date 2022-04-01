package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 10:39 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
@Scope("prototype")
public class TestEventProcessor extends AbstractEventProcessor {
    @Override
    public ProcessorId getProcessorId() {
        return ProcessorId.TEST;
    }

    @Value(value = "${demo.value:hi}")
    private String value;

    public TestEventProcessor() {
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
