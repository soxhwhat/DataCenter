package com.juphoon.rtc.datacenter.test.handler;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.ProcessorId;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.handler.AbstractEventHandler;
import com.juphoon.rtc.datacenter.datacore.handler.inner.FirstInnerHandler;
import com.juphoon.rtc.datacenter.datacore.processor.AbstractEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;

/**
 * @author ajian.zheng@juphoon.com
 * @date 2/16/22 10:19 AM
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
public class TestEventRandomSleepHandler extends AbstractEventHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.TestEventRandomSleepHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.values());
    }

    @Override
    public boolean handle(EventContext context) {
        log.info("{} handle {}", getId(), context);

        /// 随机sleep 0~1000 毫秒
        int random = new Random().nextInt(100);

        log.info("random:{}", random);

        try {
            Thread.sleep(random);
        } catch (Exception ignored) {
        }

        return true;
    }
}
