package test.com.juphoon.rtc.datacenter.datacore;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author ajian.zheng@juphoon.com
 * @date 2/16/22 10:19 AM
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
public class TestEventHandler extends AbstractEventHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.TestEventHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.values());
    }

    @Override
    public boolean handle(EventContext context) {
        log.info("{} handle {}", getId(), context);
        return true;
    }
}
