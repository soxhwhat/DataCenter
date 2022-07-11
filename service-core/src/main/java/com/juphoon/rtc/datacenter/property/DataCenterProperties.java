package com.juphoon.rtc.datacenter.property;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.juphoon.rtc.datacenter.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.processor.loader.ContextLoaderConfig;
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

    private RedisEvent redisEvent = new RedisEvent();

    /**
     * processor 列表
     */
    private List<Processor> eventProcessors = new LinkedList<>();

    /**
     * processor 列表
     */
    private List<Processor> logProcessors = new LinkedList<>();

    /**
     * processor 列表
     */
    private List<Processor> stateProcessors = new LinkedList<>();

    /**
     * 关系型数据库配置
     */
    private DataSource dataSource = new DataSource();

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

    @Getter
    @Setter
    public static class DataSource {

        private String url;

        private String driverClassName = "com.mysql.cj.jdbc.Driver";

        private String username;

        private String password;

        private int initialSize = DruidAbstractDataSource.DEFAULT_INITIAL_SIZE;

        private int minIdle = DruidAbstractDataSource.DEFAULT_MIN_IDLE;

        private int maxActive = DruidAbstractDataSource.DEFAULT_MAX_ACTIVE_SIZE;

        private boolean testOnBorrow = DruidAbstractDataSource.DEFAULT_TEST_ON_BORROW;

        private boolean testWhileIdle = DruidAbstractDataSource.DEFAULT_WHILE_IDLE;
    }

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

        /**
         * contextLoader 配置
         */
        private ContextLoaderConfig contextLoader = new ContextLoaderConfig();

        public String getName() {
            return name.getId();
        }
    }
}

