package com.juphoon.rtc.datacenter.servicecore.api;

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
    COLLECTION_CONCURRENT_ITEM_ROOM("jrtc_concurrent_room_", "房间并发信息"),

    /**
     * 核心平台房间上报话单记录
     */
    COLLECTION_EVENT_ROOM("jrtc_call_room", "核心平台房间话单集合"),

    /**
     * 核心平台成员加入离开记录
     */
    COLLECTION_EVENT_USER("jrtc_call_user", "核心平台成员加入离开集合"),

    /**
     * 测试全局一份
     */
    COLLECTION_EVENT_TEST_ONE("test_one", "测试全局一份"),

    /**
     * 宁波银行外呼日统计
     */
    COLLECTION_EVENT_EXTERNAL_PUSH_DAILY("jrtc_external_call_daily", "宁波银行外呼统计"),

    /**
     * 宁波银行外呼分时(15min/1h)统计
     */
    COLLECTION_EVENT_EXTERNAL_PUSH_PART("jrtc_external_call_part", "宁波银行外呼分时统计"),

    /**
     * 宁波银行客服话单日统计
     */
    COLLECTION_EVENT_EXTERNAL_TICKET_DAILY("jrtc_external_ticket_daily", "宁波银行客服话单统计"),

    /**
     * 宁波银行客服话单分时(15min/1h)统计
     */
    COLLECTION_EVENT_EXTERNAL_TICKET_PART("jrtc_external_ticket_part", "宁波银行客服话单分时统计"),

    /**
     * 天赛上行通话质量检测
     */
    COLLECTION_EVENT_THEA_SEND("jrtc_thea_send_", "天赛上行通话质量检测"),

    /**
     * 天赛下行通话质量检测
     */
    COLLECTION_EVENT_THEA_RECV("jrtc_thea_recv_", "天赛下行通话质量检测"),

    /**
     * 天赛加入房间成功率检测
     */
    COLLECTION_EVENT_THEA_JOIN("jrtc_thea_room_join", "天赛加房成功率检测"),

    /**
     * 天赛音视频质量监测
     */
    COLLECTION_EVENT_THEA_QUALITY("jrtc_thea_quality", "天赛音视频质量监测"),


    /**
     * 服务端异常事件
     */
    COLLECTION_SERVER_EXCEPTION_EVENT("jrtc_event_exception", "服务端异常事件"),

    /**
     * 记录坐席状态事件数据集合
     */
    COLLECTION_ACD_AGENT_STATE("jrtc_acd_agent_state_", "坐席状态事件集合"),

    /**
     * 记录队列排队状态事件数据集合
     */
    COLLECTION_ACD_QUEUE_COUNT("jrtc_acd_queue_count_", "队列排队状态事件集合"),
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
