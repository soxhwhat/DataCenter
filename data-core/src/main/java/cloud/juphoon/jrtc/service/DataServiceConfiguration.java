package cloud.juphoon.jrtc.service;

import cloud.juphoon.jrtc.handler.test.*;
import cloud.juphoon.jrtc.mq.EventQueueConfig;
import cloud.juphoon.jrtc.processor.EventProcessorBuilder;
import cloud.juphoon.jrtc.processor.impl.HttpEventProcessor;
import cloud.juphoon.jrtc.processor.impl.KafkaEventProcessor;
import cloud.juphoon.jrtc.processor.impl.MongoEventProcessor;
import cloud.juphoon.jrtc.processor.impl.MySqlEventProcessor;
import cloud.juphoon.jrtc.utils.SpringBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Configuration
public class DataServiceConfiguration {

    @Autowired
    private DataHandleBuilder dataHandleBuilder;

    @Bean
    @ConditionalOnMissingBean
    @DependsOn("SpringBeanUtils")
    public DataService config() throws Exception {
        //TODO 示例

        EventQueueConfig kafkaEventQueueConfig = new EventQueueConfig();
        EventQueueConfig httpEventQueueConfig = new EventQueueConfig();
        EventQueueConfig mongoEventQueueConfig = new EventQueueConfig();

        return DataServiceBuilder.processors()
                // TODO processor 默认配置如何实现
                .processor(EventProcessorBuilder.newProcessor(new MySqlEventProcessor())
                        .handler(dataHandleBuilder.newHandle(Ahandler.class))
                        .handler(new Bhandler())
                        .handler(new Chandler())
                        .build())
                .processor(EventProcessorBuilder.newProcessor(new MongoEventProcessor())
                        .mq(mongoEventQueueConfig)
                        .handler(new Dhandler())
                        .handler(new Ehandler())
                        .build())
                .processor(EventProcessorBuilder.newProcessor(new KafkaEventProcessor())
                        .mq(kafkaEventQueueConfig)
                        .handler(new Fhandler())
                        .handler(new Ghandler())
                        .build())
                .processor(EventProcessorBuilder.newProcessor(new HttpEventProcessor())
                        .mq(httpEventQueueConfig)
                        .handler(new Xhandler())
                        .build())
                .build();
    }
}
