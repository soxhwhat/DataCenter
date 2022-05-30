package com.juphoon.rtc.datacenter.configuration;

import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.event.storage.impl.NoneEventLogServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.*;

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
    public ILogService eventLogServiceReliable() {
        return new NoneEventLogServiceImpl();
    }

    @Bean
    @Qualifier(EVENT_BIN_LOG_IMPL_FLASH)
    public ILogService eventLogServiceFlash() {
        return new NoneEventLogServiceImpl();
    }

    @Bean
    @Qualifier(LOG_BIN_LOG_IMPL_FLASH)
    public ILogService eventLogServiceNone() {
        return new NoneEventLogServiceImpl();
    }
}
