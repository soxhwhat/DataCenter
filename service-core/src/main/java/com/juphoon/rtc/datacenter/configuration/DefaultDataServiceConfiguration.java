package com.juphoon.rtc.datacenter.configuration;

import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.entity.ServiceLevelTypeEnum;
import com.juphoon.rtc.datacenter.handle.database.acdstat.*;
import com.juphoon.rtc.datacenter.handle.http.agree.AbstractAgreeNoticeHandler;
import com.juphoon.rtc.datacenter.handle.mongo.AcdEventMongoHandler;
import com.juphoon.rtc.datacenter.handle.mongo.AcdTicketEventMongoHandler;
import com.juphoon.rtc.datacenter.handle.redis.QueueWaitRedisHandle;
import com.juphoon.rtc.datacenter.handle.redis.StaffRedisHandle;
import com.juphoon.rtc.datacenter.processor.DatabaseEventProcessor;
import com.juphoon.rtc.datacenter.processor.HttpClientEventProcessor;
import com.juphoon.rtc.datacenter.processor.MongoProcessor;
import com.juphoon.rtc.datacenter.processor.RedisProcessor;
import com.juphoon.rtc.datacenter.property.DataCenterProperties;
import com.juphoon.rtc.datacenter.service.DataService;
import com.juphoon.rtc.datacenter.service.DataServiceBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Map;

/**
 * <p>产品默认构造器</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 */
@Setter
@Getter
@Component
public class DefaultDataServiceConfiguration {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private DataCenterProperties properties;


    @Autowired
    private AcdCallInfoStatDailyHandler acdCallInfoStatDailyHandler;

    @Autowired
    private AcdCallInfoStatPart15MinHandler acdCallInfoStatPart15MinHandler;

    @Autowired
    private AcdCallInfoStatPart30MinHandler acdCallInfoStatPart30MinHandler;

    @Autowired
    private AcdCallInfoStatPartHourHandler acdCallInfoStatPartHourHandler;

    @Autowired
    private AcdAgentOpStatDailyHandler acdAgentOpStatDailyHandler;

    @Autowired
    private AcdAgentOpStatPart15MinHandler acdAgentOpStatPart15MinHandler;

    @Autowired
    private AcdAgentOpStatPart30MinHandler acdAgentOpStatPart30MinHandler;

    @Autowired
    private AcdAgentOpStatPartHourHandler acdAgentOpStatPartHourHandler;

    @Autowired
    private AcdTicketEventMongoHandler acdTicketEventMongoHandler;

    @Autowired
    private AcdEventMongoHandler acdEventMongoHandler;

    @Autowired
    private AcdAgentOpCheckinDailyByShiftHandler acdAgentOpCheckinDailyByShiftHandler;

    @Autowired
    private AcdExtServiceLevelDailyHandler acdExtServiceLevelDailyHandler;

    @Autowired
    private AcdExtServiceLevelPart15MinHandler acdExtServiceLevelPart15MinHandler;

    @Autowired
    private AcdExtServiceLevelPart30MinHandler acdExtServiceLevelPart30MinHandler;

    @Autowired
    private AcdExtServiceLevelPartHourHandler acdExtServiceLevelPartHourHandler;

    @Autowired
    private AcdExtTerminalDailyHandler acdExtTerminalDailyHandler;

    @Autowired
    private AcdExtTerminalPart15MinHandler acdExtTerminalPart15MinHandler;

    @Autowired
    private AcdExtTerminalPart30MinHandler acdExtTerminalPart30MinHandler;

    @Autowired
    private AcdExtTerminalPartHourHandler acdExtTerminalPartHourHandler;

    @Autowired
    private StaffRedisHandle staffRedisHandle;

    @Autowired
    private QueueWaitRedisHandle queueWaitRedisHandle;

    @SuppressWarnings("PMD")
    @Bean
    @ConditionalOnMissingBean
    public DataService config(Map<String, AbstractAgreeNoticeHandler> handlerMap) {

        // 赞同通知
        HttpClientEventProcessor agreeNotifyProcessor = beanFactory.getBean(HttpClientEventProcessor.class);
        agreeNotifyProcessor.setProcessorId(ProcessorId.AGREE);

        if (properties.getAgree().isEnabled()) {
            HttpClientEventProcessor.Config config = new HttpClientEventProcessor.Config();
            config.setHosts(properties.getAgree().getHosts());

            agreeNotifyProcessor.setConfig(config);
            agreeNotifyProcessor.setEnabled(properties.getAgree().isEnabled());
        }

        agreeNotifyProcessor.setEnabled(properties.getAgree().isEnabled());

        // 话务统计
        // 由于在 AbstractEventProcessor 中包含了 @Autowired，因此只能从bean工厂创建
        DatabaseEventProcessor acdEventProcessor = beanFactory.getBean(DatabaseEventProcessor.class);

        acdEventProcessor.setProcessorId(ProcessorId.ACD_STAT);
        acdEventProcessor.setEnabled(properties.getAcdStat().isEnabled());
        acdCallInfoStatDailyHandler.setEnabled(properties.getAcdStat().isCallInfoDailyEnabled());
        acdCallInfoStatPart15MinHandler.setEnabled(properties.getAcdStat().isCallInfo15minEnabled());
        acdCallInfoStatPart30MinHandler.setEnabled(properties.getAcdStat().isCallInfo30minEnabled());
        acdCallInfoStatPartHourHandler.setEnabled(properties.getAcdStat().isCallInfoHourEnabled());
        acdAgentOpStatDailyHandler.setEnabled(properties.getAcdStat().isAgentOpDailyEnabled());
        acdAgentOpStatPart15MinHandler.setEnabled(properties.getAcdStat().isAgentOp15minEnabled());
        acdAgentOpStatPart30MinHandler.setEnabled(properties.getAcdStat().isAgentOp30minEnabled());
        acdAgentOpStatPartHourHandler.setEnabled(properties.getAcdStat().isAgentOpHourEnabled());
        acdAgentOpCheckinDailyByShiftHandler.setEnabled(properties.getAcdStat().isAgentOpCheckinDailyEnabled());
        acdExtServiceLevelDailyHandler.setEnabled(properties.getAcdStat().isExtServiceLevelDailyEnabled());
        acdExtServiceLevelDailyHandler.setServiceLevelTypeEnums(properties.getAcdStat().getServiceLevelTypeEnums());
        acdExtServiceLevelPart15MinHandler.setEnabled(properties.getAcdStat().isExtServiceLevel15minEnabled());
        acdExtServiceLevelPart15MinHandler.setServiceLevelTypeEnums(properties.getAcdStat().getServiceLevelTypeEnums());
        acdExtServiceLevelPart30MinHandler.setEnabled(properties.getAcdStat().isExtServiceLevel30minEnabled());
        acdExtServiceLevelPart30MinHandler.setServiceLevelTypeEnums(properties.getAcdStat().getServiceLevelTypeEnums());
        acdExtServiceLevelPartHourHandler.setEnabled(properties.getAcdStat().isExtServiceLevelHourEnabled());
        acdExtServiceLevelPartHourHandler.setServiceLevelTypeEnums(properties.getAcdStat().getServiceLevelTypeEnums());
        acdExtTerminalDailyHandler.setEnabled(properties.getAcdStat().isExtTerminalDailyEnabled());
        acdExtTerminalPart15MinHandler.setEnabled(properties.getAcdStat().isExtTerminal15minEnabled());
        acdExtTerminalPart30MinHandler.setEnabled(properties.getAcdStat().isExtTerminal30minEnabled());
        acdExtTerminalPartHourHandler.setEnabled(properties.getAcdStat().isExtTerminalHourEnabled());

        if (!CollectionUtils.isEmpty(properties.getAcdStat().getServiceLevelTypeEnums())) {
            properties.getAcdStat().getServiceLevelTypeEnums().sort(Comparator.comparingInt(ServiceLevelTypeEnum::getServiceLevel));
        }

        /// 事件写入mongodb
        MongoProcessor mongoProcessor = beanFactory.getBean(MongoProcessor.class);
        mongoProcessor.setProcessorId(ProcessorId.MONGO);
        mongoProcessor.setEnabled(properties.getMongoEvent().isEnabled());
        acdTicketEventMongoHandler.setEnabled(properties.getMongoEvent().isAcdTicketEventEnabled());
        acdEventMongoHandler.setEnabled(properties.getMongoEvent().isAcdEventEnabled());
        // TODO 补充其他
        RedisProcessor redisProcessor = beanFactory.getBean(RedisProcessor.class);
        redisProcessor.setProcessorId(ProcessorId.REDIS);
        mongoProcessor.setEnabled(properties.getRedisEvent().isEnabled());
        staffRedisHandle.setEnabled(properties.getRedisEvent().isStaffEnabled());
        queueWaitRedisHandle.setEnabled(properties.getRedisEvent().isQueueEnabled());

        //@formatter:off
        return DataServiceBuilder.processors()
                // 赞同通知
                .processor(agreeNotifyProcessor)
                    .mq(properties.getMq().trans())
                    // 构造测试handler
                    .handler(handlerMap.get("agreeLoginNotifyHandler"))
                    .handler(handlerMap.get("agreeLogoutNotifyHandler"))
                    .handler(handlerMap.get("agreePrepareEnterRoomHandler"))
                    .handler(handlerMap.get("agreeRecordSnapshotHandler"))
                    .handler(handlerMap.get("agreeRoomNoticeHandler"))
                    .handler(handlerMap.get("agreeSendOnlineMessageHandler"))
                    .handler(handlerMap.get("agreeUserLoginRequestHandler"))
                    // todo 补充其他handler
                    .end()
                // 客服统计
                .processor(acdEventProcessor)
                    .mq(properties.getMq().trans())
                    .handler(acdCallInfoStatDailyHandler)
                    .handler(acdCallInfoStatPart15MinHandler)
                    .handler(acdCallInfoStatPart30MinHandler)
                    .handler(acdCallInfoStatPartHourHandler)
                    .handler(acdAgentOpStatDailyHandler)
                    .handler(acdAgentOpStatPart15MinHandler)
                    .handler(acdAgentOpStatPart30MinHandler)
                    .handler(acdAgentOpStatPartHourHandler)
                    .handler(acdAgentOpCheckinDailyByShiftHandler)
                    .handler(acdExtServiceLevelDailyHandler)
                    .handler(acdExtServiceLevelPart15MinHandler)
                    .handler(acdExtServiceLevelPart30MinHandler)
                    .handler(acdExtServiceLevelPartHourHandler)
                    .handler(acdExtTerminalDailyHandler)
                    .handler(acdExtTerminalPart15MinHandler)
                    .handler(acdExtTerminalPart30MinHandler)
                    .handler(acdExtTerminalPartHourHandler)
                    .end()
                .processor(mongoProcessor)
                    .mq(properties.getMq().trans())
                    .handler(acdEventMongoHandler)
                    .handler(acdTicketEventMongoHandler)
//                    .handler(theaMongoHandler)
                    .end()
                .build();
        //@formatter:on
    }
}
