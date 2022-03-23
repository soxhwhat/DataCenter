package com.juphoon.rtc.datacenter.configuration;

import com.juphoon.rtc.datacenter.handle.database.acdstat.AcdCallInfoStatDailyHandler;
import com.juphoon.rtc.datacenter.handle.database.acdstat.AcdCallInfoStatPart15MinHandler;
import com.juphoon.rtc.datacenter.handle.database.acdstat.AcdCallInfoStatPart30MinHandler;
import com.juphoon.rtc.datacenter.handle.database.acdstat.AcdCallInfoStatPartHourHandler;
import com.juphoon.rtc.datacenter.handle.http.agree.AgreeLoginNotifyHandler;
import com.juphoon.rtc.datacenter.handle.http.agree.AgreeLogoutNotifyHandler;
import com.juphoon.rtc.datacenter.handle.http.agree.AgreeUserLoginRequestHandler;
import com.juphoon.rtc.datacenter.mapper.AcdCallInfoStatDailyMapper;
import com.juphoon.rtc.datacenter.processor.DatabaseEventProcessor;
import com.juphoon.rtc.datacenter.processor.HttpClientEventProcessor;
import com.juphoon.rtc.datacenter.property.JrtcDataCenterProperties;
import com.juphoon.rtc.datacenter.service.DataService;
import com.juphoon.rtc.datacenter.service.DataServiceBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Setter
@Getter
@Component
public class DefaultDataServiceConfiguration {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private JrtcDataCenterProperties properties;

    @Autowired
    private AcdCallInfoStatDailyHandler acdCallInfoStatDailyHandler;

    @Autowired
    private AcdCallInfoStatPart15MinHandler acdCallInfoStatPart15MinHandler;

    @Autowired
    private AcdCallInfoStatPart30MinHandler acdCallInfoStatPart30MinHandler;

    @Autowired
    private AcdCallInfoStatPartHourHandler acdCallInfoStatPartHourHandler;

    @Bean
    @ConditionalOnMissingBean
    public DataService config() {

        // 赞同通知
        HttpClientEventProcessor agreeNotifyProcessor = beanFactory.getBean(HttpClientEventProcessor.class);
        agreeNotifyProcessor.setName("赞同通知处理器");

        if (properties.getAgree().isEnabled()) {
            HttpClientEventProcessor.Config config = new HttpClientEventProcessor.Config();
            config.setHosts(properties.getAgree().getHosts());

            agreeNotifyProcessor.setConfig(config);
        }

        agreeNotifyProcessor.setEnabled(properties.getAgree().isEnabled());

        // 话务统计
        DatabaseEventProcessor acdEventProcessor = new DatabaseEventProcessor();
        acdEventProcessor.setName("客服统计");
        acdEventProcessor.setEnabled(properties.getAcdStat().isEnabled());
        acdCallInfoStatDailyHandler.setEnabled(properties.getAcdStat().isCallInfoDailyEnabled());
        acdCallInfoStatPart15MinHandler.setEnabled(properties.getAcdStat().isCallInfo15minEnabled());
        acdCallInfoStatPart30MinHandler.setEnabled(properties.getAcdStat().isCallInfo30minEnabled());
        acdCallInfoStatPartHourHandler.setEnabled(properties.getAcdStat().isCallInfoHourEnabled());

        // TODO 补充其他

        //@formatter:off
        return DataServiceBuilder.processors()
                // 赞同通知
                .processor(agreeNotifyProcessor)
                    // 构造测试handler
                    .handler(new AgreeLoginNotifyHandler())
                    .handler(new AgreeLogoutNotifyHandler())
                    .handler(new AgreeUserLoginRequestHandler())
                    // todo 补充其他handler
                    .end()
                // 客服统计
                .processor(acdEventProcessor)
                    .handler(acdCallInfoStatDailyHandler)
                    .handler(acdCallInfoStatPart15MinHandler)
                    .handler(acdCallInfoStatPart30MinHandler)
                    .handler(acdCallInfoStatPartHourHandler)
                    .end()
                .build();
        //@formatter:on
    }
}
