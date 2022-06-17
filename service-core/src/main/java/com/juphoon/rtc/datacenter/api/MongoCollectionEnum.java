package com.juphoon.rtc.datacenter.api;

/**
 * <p>Mongodb集合定义枚举</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 6/10/22 5:55 PM
 */
public enum MongoCollectionEnum {
    /**
     * 事件数据集合前缀(按天拆分集合)
     */
    COLLECTION_EVENT_PREFIX("jrtc.events_", "事件数据集合前缀"),

    /**
     * 埋点数据集合
     */
    COLLECTION_MD("jrtc.md.events", "埋点数据集合"),

    /**
     * 广发录制事件数据集合
     */
    COLLECTION_B03_RECORDS("jrtc.cloud.recordingEvents", "B03录制事件数据集合"),

    /**
     * 广发话单事件数据集合
     */
    COLLECTION_B03_TICKETS("jrtc.records", "B03话单事件数据集合"),

    /**
     * 兼容老平台终端日志收集数据集合
     */
    COLLECTION_LOG("log_collection", "兼容老平台终端日志收集数据集合"),

    /**
     * 兼容老平台终端日志收集数据集合
     */
    COLLECTION_LOG_INFO("info_collection", "兼容老平台终端日志收集数据集合"),

    /**
     * 测试按天拆分
     */
    COLLECTION_EVENT_TEST_DAILY("test_daily_", "测试按天拆分"),

    /**
     * 并发记录
     */
    COLLECTION_CONCURRENT_ITEM_ROOM("jrtc_concurrent_room", "房间并发信息"),

    /**
     * 测试全局一份
     */
    COLLECTION_EVENT_TEST_ONE("test_one", "测试全局一份"),
    ;

    private final String name;

    private final String desc;

    MongoCollectionEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * 集合名
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 描述信息
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }
}
