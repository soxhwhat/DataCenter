package com.juphoon.rtc.datacenter.api;

/**
 * <p>handler统一管理</p>
 * <p>方便重做时ID唯一匹配，因此这里的ID不能重名</p>
 *
 * @author ajian.zheng
 * @since 2022-03-24
 */
public enum HandlerId {
    /**
     * TestHttpHandler
     */
    TestHttpEventHandler("testHttpEventHandler", "testHttpEventHandler"),

    /**
     * Xhandler
     */
    Xhandler("xhandler", "xhandler"),

    /**
     * yhandler
     */
    Yhandler("yhandler", "yhandler"),

    /**
     * 15分钟话务汇总handler
     */
    AcdCallInfoStatPart15MinHandler("acdCallInfoStatPart15MinHandler", "15分钟话务汇总handler"),

    /**
     * 30分钟话务汇总handler
     */
    AcdCallInfoStatPart30MinHandler("acdCallInfoStatPart30MinHandler", "30分钟话务汇总handler"),

    /**
     * 小时话务汇总handler
     */
    AcdCallInfoStatPartHourHandler("acdCallInfoStatPartHourHandler", "小时话务汇总handler"),

    /**
     * 话务日汇总handler
     */
    AcdCallInfoStatDailyHandler("acdCallInfoStatDailyHandler", "话务日汇总handler"),

    /**
     * 15分钟坐席汇总handler
     */
    AcdAgentOpStatPart15MinHandler("acdAgentOpStatPart15MinHandler", "15分钟坐席汇总handler"),

    /**
     * 30分钟坐席汇总handler
     */
    AcdAgentOpStatPart30MinHandler("acdAgentOpStatPart30MinHandler", "30分钟坐席汇总handler"),

    /**
     * 小时坐席汇总handler
     */
    AcdAgentOpStatPartHourHandler("acdAgentOpStatPartHourHandler", "小时坐席汇总handler"),

    /**
     * 坐席日汇总handler
     */
    AcdAgentOpStatDailyHandler("acdAgentOpStatDailyHandler", "坐席日汇总handler"),

    /**
     * 坐席签入签出日汇总handler
     */
    AcdAgentOpCheckinDailyByShiftHandler("acdAgentOpCheckinDailyByShiftHandler", "坐席签入签出按班次日汇总handler"),

    /**
     * 接通量日汇总handler
     */
    AcdExtServiceLevelDailyHandler("acdExtServiceLevelDailyHandler", "接通量日汇总handler"),

    /**
     * 接通量15分钟汇总handler
     */
    AcdExtServiceLevelPart15MinHandler("acdExtServiceLevelPart15MinHandler", "接通量15分钟汇总handler"),

    /**
     * 接通量30分钟汇总handler
     */
    AcdExtServiceLevelPart30MinHandler("acdExtServiceLevelPart30MinHandler", "接通量30分钟汇总handler"),

    /**
     * 接通量小时汇总handler
     */
    AcdExtServiceLevelPartHourHandler("acdExtServiceLevelPartHourHandler", "接通量小时汇总handler"),

    /// 赞同

    /**
     * 1.1 客户端用户登录认证
     * <p>userlogin_request</p>
     */
    AgreeUserLoginRequestHandler("agreeUserLoginRequestHandler", "赞同登录请求handler"),

    /**
     * 1.2 客户端用户登录通知
     * <p>userlogin_notify</p>
     */
    AgreeLoginNotifyHandler("agreeLoginNotifyHandler", "赞同客户端用户登录通知handler"),

    /**
     * 1.3 进房间验证通知
     * <p>prepare_enterroom</p>
     */
    AgreePrepareEnterRoomHandler("agreePrepareEnterRoomHandler", "赞同进房间验证通知handler"),

    /**
     * 1.4 进出房间通知
     * <p>room_notify</p>
     */
    AgreeRoomNotifyHandler("agreeRoomNotifyHandler", "赞同进出房间通知handler"),

    /**
     * 1.5 透明通道数据通知
     * <p>transbuffer_notify</p>
     */
    AgreeTransBufferNotifyHandler("agreeTransBufferNotifyHandler", "赞同透明通道数据通知handler"),

    /**
     * 1.7 录像或拍照结束通知
     * <p>recordsnapshot_notify</p>
     */
    AgreeRecordSnapShotNotifyHandler("agreeRecordSnapShotNotifyHandler", "赞同录像或拍照结束通知handler"),

    /**
     * 1.8 客户端用户签退通知
     * <p>userlogout_notify</p>
     */
    AgreeUserLogoutNotifyHandler("agreeUserLogoutNotifyHandler", "赞同客户端用户签退通知handler"),


    /// 赞同 end


    /**
     * 西亚mongoHandler
     */
    TheaMongoHandler("theaMongoHandler", "西亚mongoHandler"),

    /**
     * 客服话单事件
     */
    AcdTicketEventMongoHandler("acdTicketEventMongoHandler", "客服话单事件mongodb入库Handler"),

    /**
     * 客服事件mongodb入库handler
     */
    AcdEventMongoHandler("acdEventMongoHandler", "客服事件mongodb入库handler"),

    /**
     * 终端埋点事件mongodb入库handler
     */
    MdEventMongoHandler("mdEventMongoHandler", "终端埋点事件mongodb入库handler"),

    /**
     * 录制事件mongodb入库handler
     */
    AcdRecordEventMongoHandler("acdRecordEventMongoHandler", "客服事件mongodb入库handler"),

    /**
     *
     */
    QueueStatusKafkaHandler("queueStatusKafkaHandler", "队列状态kafka事件Handler"),
    /**
     *
     */
    StaffStatusKafkaHandler("staffStatusKafkaHandler", "坐席状态kafka事件Handler"),
    /**
     *
     */
    TicketKafkaHandler("ticketKafkaHandler", "话单kafka事件Handler"),


    /**
     * 内部处理handler
     */
    FIRST("innerFirst", "内部处理handler-first"),

    /**
     * 内部处理handler
     */
    LAST("innerLast", "内部处理handler-last");

    /**
     * 类型
     */
    private String name;

    /**
     * 间隔
     */
    private String id;

    HandlerId(String id, String name) {
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
