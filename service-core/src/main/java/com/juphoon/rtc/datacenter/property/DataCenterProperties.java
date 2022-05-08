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
@ConfigurationProperties(prefix = JrtcDataCenterConstant.DATA_CENTER_CONFIG_PREFIX)
@Data
public class DataCenterProperties {

    private Agree agree = new Agree();

    private AcdStat acdStat = new AcdStat();

    private Mq mq = new Mq();

    private MongoEvent mongoEvent = new MongoEvent();

    private KafkaEvent kafkaEvent = new KafkaEvent();

    private RedisEvent redisEvent = new RedisEvent();

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

    /**
     * 客服报表统计
     */
    @Data
    public static class AcdStat {
        /**
         * 客服报表统计开关(默认打开)
         */
        private boolean enabled = true;

        /**
         * 15分钟话务统计
         */
        private boolean callInfo15minEnabled = false;

        /**
         * 30分钟话务统计
         */
        private boolean callInfo30minEnabled = false;

        /**
         * 小时话务统计
         */
        private boolean callInfoHourEnabled = false;

        /**
         * 天话务统计
         */
        private boolean callInfoDailyEnabled = true;


        /**
         * 15分钟坐席统计
         */
        private boolean agentOp15minEnabled = false;

        /**
         * 30分钟坐席统计
         */
        private boolean agentOp30minEnabled = false;

        /**
         * 小时坐席统计
         */
        private boolean agentOpHourEnabled = false;

        /**
         * 天坐席统计
         */
        private boolean agentOpDailyEnabled = true;

        /**
         * 天坐席签入签出记录
         */
        private boolean agentOpCheckinDailyEnabled = true;

        /**
         * 天服务水平统计
         */
        private boolean extServiceLevelDailyEnabled = false;

        /**
         * 15分钟服务水平统计
         */
        private boolean extServiceLevel15minEnabled = false;

        /**
         * 30分钟服务水平统计
         */
        private boolean extServiceLevel30minEnabled = false;

        /**
         * 小时服务水平统计
         */
        private boolean extServiceLevelHourEnabled = false;

        /**
         * 天渠道平台统计
         */
        private boolean extTerminalDailyEnabled = false;

        /**
         * 15分钟渠道平台统计
         */
        private boolean extTerminal15minEnabled = false;

        /**
         * 30分钟渠道平台统计
         */
        private boolean extTerminal30minEnabled = false;

        /**
         * 小时渠道平台统计
         */
        private boolean extTerminalHourEnabled = false;

        /**
         * 默认20s,30s服务水平
         */
        private List<ServiceLevelTypeEnum> serviceLevelTypeEnums = Arrays.asList(SERVICE_LEVEL_20SEC, SERVICE_LEVEL_30SEC);

    }
    //iron.datacenter.acd-stat.enabled=true


    @Data
    public static class Mq {
        /**
         * 类型
         * simple | distuptor(default)
         */
        private String type = "simple";

        /**
         * 队列大小
         */
        private int capacity = 1024;

        /**
         * 数据库文件名
         */
        private String dbName = "datacenter.db";

        /**
         * 转化为内部类
         *
         * @return
         */
        public EventQueueConfig trans() {
            EventQueueConfig config = new EventQueueConfig();

            config.setType(type);
            config.setQueueSize(capacity);
            config.setDbName(dbName);

            return config;
        }
    }

    @Data
    public static class MongoEvent {
        /**
         * MongoDB事件开关
         */
        private boolean enabled = true;

        /**
         * 客服事件开关
         */
        private boolean acdEventEnabled = true;

        /**
         * 话单事件开关
         */
        private boolean acdTicketEventEnabled = true;

        private boolean mdEventEnabled = true;
    }

    @Data
    public static class KafkaEvent {
        /**
         * kafka事件开关
         */
        private boolean enabled = true;

        /**
         * 客服事件开关
         */
        private boolean staffEnabled = true;

        /**
         * 话单事件开关
         */
        private boolean ticketEnabled = true;

        /**
         * 话单事件开关
         */
        private boolean queueEnabled = true;
    }

    @Data
    public static class RedisEvent {
        /**
         * redis事件开关
         */
        private boolean enabled = true;

        /**
         * 客服事件开关
         */
        private boolean staffEnabled = true;

        /**
         * 话单事件开关
         */
        private boolean queueEnabled = true;
    }

}

