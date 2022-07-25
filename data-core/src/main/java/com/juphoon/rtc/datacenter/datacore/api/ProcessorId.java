package com.juphoon.rtc.datacenter.datacore.api;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.*;

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
    AGREE("agreeNotifyProcessor", "赞同通知处理器", PROCESSOR_TYPE_EVENT),

    /**
     * 客服统计
     */
    ACD_STAT("acdStatProcessor", "客服统计理器", PROCESSOR_TYPE_EVENT),

    /**
     * 事件mongo入库
     */
    MONGO_EVENT_PROCESSOR("mongoEventProcessor", "Mongo事件入库", PROCESSOR_TYPE_EVENT),

    /**
     * 事件mongo入库
     * TODO 可能不是
     */
    KAFKA("kafkaProcessor", "Kafka事件推送", PROCESSOR_TYPE_EVENT),

    /**
     * 终端埋点
     */
//    MD("mdProcessor", "终端埋点处理器", PROCESSOR_TYPE_EVENT),

    /**
     * 日志mongo入库
     */
    MONGO_LOG_PROCESSOR("mongoLogProcessor", "兼容旧版本日志收集处理器", PROCESSOR_TYPE_LOG),

    /**
     * 实时状态Redis处理器
     */
    STATE_RDB("stateRdbProcessor", "实时状态Rdb处理器", PROCESSOR_TYPE_STATE),

    /**
     * 实时状态 MongoDB 持久化处理器
     */
    STATE_MONGO("mongoStateProcessor", "实时状态Mongo处理器", PROCESSOR_TYPE_STATE),

    /**
     * 测试
     */
    TEST_EVENT("testEventProcessor", "事件测试处理器", PROCESSOR_TYPE_TEST),

    /**
     * 测试
     */
    TEST_FLASH_EVENT_COUNTER("testFlashEventCounterProcessor", "flash事件带计数测试处理器", PROCESSOR_TYPE_TEST),

    /**
     * 测试
     */
    TEST_LOG("testLogProcessor", "日志测试处理器", PROCESSOR_TYPE_TEST),

    /**
     * 测试
     */
    TEST_STATE("testStateProcessor", "状态测试处理器", PROCESSOR_TYPE_TEST);

    /**
     * 名称
     */
    private final String name;

    /**
     * id
     */
    private final String id;

    /**
     * 类型
     */
    private final String type;

    ProcessorId(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }
}
