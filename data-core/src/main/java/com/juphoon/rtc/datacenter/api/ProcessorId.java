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
    AGREE("agreeNoticeProcessor", "赞同通知处理器"),

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
     * 测试
     */
    TEST("testProcessor", "测试处理器");

    /**
     * 类型
     */
    private final String name;

    /**
     * 间隔
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
