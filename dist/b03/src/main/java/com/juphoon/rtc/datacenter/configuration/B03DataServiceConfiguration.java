package com.juphoon.rtc.datacenter.configuration;

import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.entity.ServiceLevelTypeEnum;
import com.juphoon.rtc.datacenter.handle.database.acdstat.*;
import com.juphoon.rtc.datacenter.handle.http.agree.AbstractAgreeNoticeHandler;
import com.juphoon.rtc.datacenter.handle.kafka.QueueStatusKafkaHandler;
import com.juphoon.rtc.datacenter.handle.kafka.StaffStatusKafkaHandler;
import com.juphoon.rtc.datacenter.handle.kafka.TicketKafkaHandler;
import com.juphoon.rtc.datacenter.handle.mongo.AcdEventMongoHandler;
import com.juphoon.rtc.datacenter.handle.mongo.AcdRecordEventMongoHandler;
import com.juphoon.rtc.datacenter.handle.mongo.AcdTicketEventMongoHandler;
import com.juphoon.rtc.datacenter.handle.mongo.MdEventMongoHandler;
import com.juphoon.rtc.datacenter.mq.EventQueueConfig;
import com.juphoon.rtc.datacenter.processor.DatabaseEventProcessor;
import com.juphoon.rtc.datacenter.processor.KafkaProcessor;
import com.juphoon.rtc.datacenter.processor.MongoProcessor;
import com.juphoon.rtc.datacenter.property.DataCenterProperties;
import com.juphoon.rtc.datacenter.service.DataService;
import com.juphoon.rtc.datacenter.service.DataServiceBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Map;

/**
 * <p>B03构造器</p>
 *
 * @author Yuan wenjun
 * @date 2022/04/28
 */
@Setter
@Getter
@Component
public class B03DataServiceConfiguration {

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
    private AcdRecordEventMongoHandler acdRecordEventMongoHandler;

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
    private StaffStatusKafkaHandler staffStatusKafkaHandler;

    @Autowired
    private QueueStatusKafkaHandler queueStatusKafkaHandler;

    @Autowired
    private TicketKafkaHandler ticketKafkaHandler;

    @Autowired
    private MdEventMongoHandler mdEventMongoHandler;

    @SuppressWarnings("PMD")
    @Bean
    public DataService config(Map<String, AbstractAgreeNoticeHandler> handlerMap) {
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

        if (!CollectionUtils.isEmpty(properties.getAcdStat().getServiceLevelTypeEnums())) {
            properties.getAcdStat().getServiceLevelTypeEnums().sort(Comparator.comparingInt(ServiceLevelTypeEnum::getServiceLevel));
        }

        /// 事件写入mongodb
        MongoProcessor mongoProcessor = beanFactory.getBean(MongoProcessor.class);
        mongoProcessor.setProcessorId(ProcessorId.MONGO);
        mongoProcessor.setEnabled(properties.getMongoEvent().isEnabled());
        acdTicketEventMongoHandler.setEnabled(properties.getMongoEvent().isAcdTicketEventEnabled());
        acdEventMongoHandler.setEnabled(properties.getMongoEvent().isAcdEventEnabled());
        acdRecordEventMongoHandler.setEnabled(true);
        mdEventMongoHandler.setEnabled(properties.getMongoEvent().isMdEventEnabled());

        //事件写入kafka
        KafkaProcessor kafkaProcessor = beanFactory.getBean(KafkaProcessor.class);
        kafkaProcessor.setProcessorId(ProcessorId.KAFKA);
        kafkaProcessor.setEnabled(properties.getKafkaEvent().isEnabled());
        queueStatusKafkaHandler.setEnabled(properties.getKafkaEvent().isQueueEnabled());
        staffStatusKafkaHandler.setEnabled(properties.getKafkaEvent().isStaffEnabled());
        ticketKafkaHandler.setEnabled(properties.getKafkaEvent().isTicketEnabled());

        EventQueueConfig kafkaConfig = new DataCenterProperties.Mq().trans();
        kafkaConfig.setType(JrtcDataCenterConstant.DATA_CENTER_QUEUE_MODE_DISRUPTOR);
        //@formatter:off
        return DataServiceBuilder.processors()
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
                    .end()
                .processor(mongoProcessor)
                    .mq(properties.getMq().trans())
                    .handler(acdEventMongoHandler)
                    .handler(acdTicketEventMongoHandler)
                    .handler(acdRecordEventMongoHandler)
                    .handler(mdEventMongoHandler)
                    .end()
                .processor(kafkaProcessor)
                    .mq(kafkaConfig)
                    .handler(queueStatusKafkaHandler)
                    .handler(ticketKafkaHandler)
                    .handler(staffStatusKafkaHandler)
                    .end()
                .build();
        //@formatter:on
    }
}
