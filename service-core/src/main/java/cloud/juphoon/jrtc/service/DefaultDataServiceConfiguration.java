package cloud.juphoon.jrtc.service;

import cloud.juphoon.jrtc.handle.kafka.EventKafkaHandler;
import cloud.juphoon.jrtc.handle.mongo.EventMongoHandler;
import cloud.juphoon.jrtc.handler.AbstractEventHandler;
import cloud.juphoon.jrtc.processor.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.Map;

import static cloud.juphoon.jrtc.constant.JrtcDataCenterConstant.*;

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
@Configuration
@ConfigurationProperties(prefix = "iron")
public class DefaultDataServiceConfiguration {

    @Autowired
    private DataSpringObjBuilder dataSpringObjBuilder;

    public Map<String, Map> event = new HashMap();

    @Bean
    @DependsOn("SpringBeanUtils")
    public DataService config(Map<String, AbstractEventProcessor> processorMap,
                              Map<String, AbstractEventHandler> handlerMap) throws Exception {
        //TODO 示例
        MongoProcessor.Config mongoConfig = new MongoProcessor.Config();
        mongoConfig.collectionMap = event.get("mongo");
        KafkaProcessor.Config kafkaConfig = new KafkaProcessor.Config();
        kafkaConfig.collectionMap = event.get("kafka");


        return DataServiceBuilder.processors()
                // TODO processor 默认配置如何实现
                .processor(EventProcessorBuilder.newProcessor(processorMap.get(MONGO_PROCESSOR))
                        .handler(handlerMap.get(EVENT_MONGO_HANDLER))
                        .build())
                .processor(EventProcessorBuilder.newProcessor(processorMap.get(KAFKA_PROCESSOR))
                        .handler(handlerMap.get(EVENT_KAFKA_HANDLER))
                        .build())
                .processor(EventProcessorBuilder.newProcessor(processorMap.get(SQL_EVENT_PROCESSOR))
                        .handler(handlerMap.get(CALLINFO_STAT_DAILY))
//                        .handler(handlerMap.get(JrtcConstant.CALLINFO_STAT_PART))
                        .build())
                .processor(EventProcessorBuilder.newProcessor(processorMap.get(NOTICE_PROCESSOR))
                        .handler(handlerMap.get(NOTICE_HANDLER))
                        .handler(handlerMap.get(SEND_ONLINE_HANDLER))
                        .build())
                .build();
    }
}
