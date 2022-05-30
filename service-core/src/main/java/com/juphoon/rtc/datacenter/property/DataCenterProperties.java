package com.juphoon.rtc.datacenter.property;

import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.processor.queue.QueueServiceConfig;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>dataCenter默认配置</p>
 * java配置的优先级低于yml配置；如果yml配置不存在，会采用java配置
 *
 * @author ajian.zheng
 * @date 2022-03-22
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = JrtcDataCenterConstant.DATA_CENTER_CONFIG_PREFIX)
public class DataCenterProperties {

    private Agree agree = new Agree();

    /**
     * processor 列表
     */
    private List<Processor> eventProcessors = new LinkedList<>();

    /**
     * processor 列表
     */
    private List<Processor> logProcessors = new LinkedList<>();

    /**
     * 赞同
     */
    @Data
    public static class Agree {
        /**
         * 赞同通知开关(默认关闭)
         */
        private boolean enabled = false;

        /**
         * 赞同主机列表
         */
        private List<String> hosts;
    }
    // iron.datacenter.agree.enabled=true
    // iron.datacenter.

    @Data
    public static class RedisEvent {
        /**
         * 坐席过期时间
         */
        private Duration staffExpireTime = Duration.ofHours(2);

        /**
         * 队列过期时间
         */
        private Duration queueExpireTime = Duration.ofSeconds(15);
    }

    @Getter
    @Setter
    public static class Processor {
        /**
         * processor 名称
         */
        private ProcessorId name;

        private QueueServiceConfig queueService = new QueueServiceConfig();

        /**
         * 处理句柄集合
         */
        private List<HandlerId> handlers;

        public String getName() {
            return name.getId();
        }
    }
}

