package com.juphoon.rtc.datacenter.property;

import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.mq.EventQueueConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

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

}

