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
import java.util.concurrent.atomic.AtomicInteger;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;

/**
 * @author ajian.zheng@juphoon.com
 * @date 2/16/22 10:19 AM
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
@SuppressWarnings("PMD")
public class TestEventRandomFailHandler extends AbstractEventHandler {
    public final static AtomicInteger COUNTER = new AtomicInteger(0);

    @Override
    public HandlerId handlerId() {
        return HandlerId.TestEventRandomFailHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.values());
    }

    @Override
    public boolean handle(EventContext context) {
        log.debug("handle id:{}, redo:{}", context.getId(), context.isRedoEvent());

        /// 随机 0~100 毫秒
        int random = new Random().nextInt(100);

        // 50% 失败
        if (random > 50) {
            log.debug("id:{} random fail", context.getId());

            return false;
        }

        log.debug("id:{} success, redo:{}, cnt:{}", context.getId(), context.isRedoEvent(), COUNTER.incrementAndGet());

        return true;
    }
}
