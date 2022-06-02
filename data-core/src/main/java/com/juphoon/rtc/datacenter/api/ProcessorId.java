package com.juphoon.rtc.datacenter.api;

/**
 * <p>处理器统一管理</p>
 * <p>方便重做时ID唯一匹配，因此这里的ID不能重名</p>
 *
 * @author ajian.zheng
 * @since 2022-03-24
 */
public enum ProcessorId {

    /**
     * 赞同通知处理器
     */
    AGREE("agreeNotifyProcessor", "赞同通知处理器"),

    /**
     * 客服统计
     */
    ACD_STAT("acdStatProcessor", "客服统计理器"),

    /**
     * 事件mongo入库
     */
    MONGO("mongoProcessor", "Mongo事件入库"),

    /**
     * 事件mongo入库
     */
    KAFKA("kafkaProcessor", "Kafka事件推送"),

    /**
     * 状态redis入库
     */
    REDIS("redisProcessor", "Redis状态记录"),

    /**
     * 终端埋点
     */
    MD("mdProcessor", "终端埋点处理器"),

    /**
     * 事件mongo入库
     */
    EXT_LOG("logProcessor", "兼容旧版本日志收集处理器"),

    /**
     * 实时状态Redis处理器
     */
    STATE_REDIS("stateRedisProcessor", "实时状态Redis处理器"),

    /**
     * 实时状态 MongoDB 持久化处理器
     */
    STATE_MONGO("stateMongoProcessor", "实时状态Mongo处理器"),

    /**
     * 测试
     */
    TEST_EVENT("testEventProcessor", "事件测试处理器"),

    /**
     * 测试
     */
    TEST_LOG("testLogProcessor", "日志测试处理器"),

    /**
     * 测试
     */
    TEST_STATE("testStateProcessor", "状态测试处理器");

    /**
     * 名称
     */
    private final String name;

    /**
     * id
     */
    private final String id;

    ProcessorId(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获取 Processor 名字
     *
     * @return
     */
    public String getName() {
        return name;
    }


    /**
     * 获取 Processor ID
     *
     * @return
     */
    public String getId() {
        return id;
    }
}
