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
public class TestEventRandomSleepAndFailHandler extends AbstractEventHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.TestEventRandomSleepAndFailHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.values());
    }

    @Override
    public boolean handle(EventContext context) {
        log.info("{} handle {}", getId(), context);

        /// 随机sleep 0~1000 毫秒
        int random = new Random().nextInt(1000);

        log.info("random:{}", random);

        try {
            Thread.sleep(random);
        } catch (Exception ignored) {
        }

//        // 5% 失败
//        if (random > 950) {
//            return false;
//        }

        return true;
    }

    /**
     * @author ajian.zheng@juphoon.com
     * @date 2/18/22 10:39 AM
     * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    @Slf4j
    @Component
    @ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
    public static class TestFlashEventCounterProcessor extends AbstractEventProcessor {
        @Autowired
        @Qualifier(EVENT_BIN_LOG_IMPL_FLASH)
        private ILogService<EventContext> eventLogService;

        @Override
        public ILogService<EventContext> logService() {
            return eventLogService;
        }

        @Override
        public ProcessorId processorId() {
            return ProcessorId.TEST_FLASH_EVENT_COUNTER;
        }

        @Override
        public FirstInnerHandler<EventContext> firstInnerEventHandler() {
            return new FirstInnerHandler<>(this);
        }
    }
}
