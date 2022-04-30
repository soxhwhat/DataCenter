package com.juphoon.rtc.datacenter.property;

import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.entity.ServiceLevelTypeEnum;
import com.juphoon.rtc.datacenter.mq.EventQueueConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.entity.ServiceLevelTypeEnum.SERVICE_LEVEL_20SEC;
import static com.juphoon.rtc.datacenter.entity.ServiceLevelTypeEnum.SERVICE_LEVEL_30SEC;

/**
 * <p>dataCenter默认配置</p>
 * java配置的优先级低于yml配置；如果yml配置不存在，会采用java配置
 *
 * @author ajian.zheng
 * @date 2022-03-22
 */
@Component
@ConfigurationProperties(prefix = JrtcDataCenterConstant.KAFKA_PREFIX)
@Data
public class KafkaProperties {

    private Topic topic = new Topic();

    /**
     * 赞同
     */
    @Data
    public static class Topic {

        private String queue = "juphoon.monitor.status.queue_status";

        private String staff = "juphoon.monitor.status.agent_status";

        private String ticket = "juphoon.monitor.event.ticket_event";

    }
}

