package com.juphoon.rtc.datacenter.configuration;

import com.juphoon.rtc.datacenter.event.queue.impl.NoneEventQueueServiceImpl;
import com.juphoon.rtc.datacenter.event.storage.IEventLogService;
import com.juphoon.rtc.datacenter.event.storage.impl.NoneEventLogServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.*;

/**
 * <p>产品默认构造器</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 */
@Configuration
public class EventBinLogConfiguration {

    @Bean
    @Primary
    @Qualifier(EVENT_BIN_LOG_IMPL_RELIABLE)
    public IEventLogService eventLogServiceReliable() {
        return new NoneEventLogServiceImpl();
    }

    @Bean
    @Qualifier(EVENT_BIN_LOG_IMPL_FLASH)
    public IEventLogService eventLogServiceFlash() {
        return new NoneEventLogServiceImpl();
    }

    @Bean
    @Qualifier(EVENT_BIN_LOG_IMPL_NONE)
    public IEventLogService eventLogServiceNone() {
        return new NoneEventLogServiceImpl();
    }
}
