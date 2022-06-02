package com.juphoon.rtc.datacenter.api;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.*;

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
//    TestHttpEventHandler("testHttpEventHandler", "testHttpEventHandler"),

    /**
     * testEventHandler 测试用
     */
    TestEventHandler("testEventHandler", "testEventHandler", RESOURCE_SCOPE_TEST),

    /**
     * testLogHandler 测试用
     */
    TestLogHandler("testLogHandler", "testLogHandler", RESOURCE_SCOPE_TEST),

    /**
     * testStateHandler 测试用
     */
    TestStateHandler("testStateHandler", "testStateHandler", RESOURCE_SCOPE_TEST),

    /**
     * 15分钟话务汇总handler
     */
    AcdCallInfoStatPart15MinHandler("acdCallInfoStatPart15MinHandler",
            "15分钟话务汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 30分钟话务汇总handler
     */
    AcdCallInfoStatPart30MinHandler("acdCallInfoStatPart30MinHandler",
            "30分钟话务汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 小时话务汇总handler
     */
    AcdCallInfoStatPartHourHandler("acdCallInfoStatPartHourHandler",
            "小时话务汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 话务日汇总handler
     */
    AcdCallInfoStatDailyHandler("acdCallInfoStatDailyHandler",
            "话务日汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 15分钟坐席汇总handler
     */
    AcdAgentOpStatPart15MinHandler("acdAgentOpStatPart15MinHandler",
            "15分钟坐席汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 30分钟坐席汇总handler
     */
    AcdAgentOpStatPart30MinHandler("acdAgentOpStatPart30MinHandler",
            "30分钟坐席汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 小时坐席汇总handler
     */
    AcdAgentOpStatPartHourHandler("acdAgentOpStatPartHourHandler",
            "小时坐席汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 坐席日汇总handler
     */
    AcdAgentOpStatDailyHandler("acdAgentOpStatDailyHandler",
            "坐席日汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 坐席签入签出日汇总handler
     */
    AcdAgentOpCheckinDailyByShiftHandler("acdAgentOpCheckinDailyByShiftHandler",
            "坐席签入签出按班次日汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 服务水平日汇总handler
     */
    AcdExtServiceLevelDailyHandler("acdExtServiceLevelDailyHandler",
            "服务水平日汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 服务水平15分钟汇总handler
     */
    AcdExtServiceLevelPart15MinHandler("acdExtServiceLevelPart15MinHandler",
            "服务水平15分钟汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 服务水平30分钟汇总handler
     */
    AcdExtServiceLevelPart30MinHandler("acdExtServiceLevelPart30MinHandler",
            "服务水平30分钟汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 服务水平小时汇总handler
     */
    AcdExtServiceLevelPartHourHandler("acdExtServiceLevelPartHourHandler",
            "服务水平小时汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 渠道平台日汇总handler
     */
    AcdExtTerminalDailyHandler("acdExtTerminalDailyHandler",
            "渠道平台日汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 渠道平台15分钟汇总handler
     */
    AcdExtTerminalPart15MinHandler("acdExtTerminalPart15MinHandler",
            "渠道平台15分钟汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 渠道平台30分钟汇总handler
     */
    AcdExtTerminalPart30MinHandler("acdExtTerminalPart30MinHandler",
            "渠道平台30分钟汇总handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 渠道平台小时汇总handler
     */
    AcdExtTerminalPartHourHandler("acdExtTerminalPartHourHandler",
            "渠道平台小时汇总handler", RESOURCE_SCOPE_GLOBAL),

    /// 赞同

    /**
     * 1.1 客户端用户登录认证
     * <p>userlogin_request</p>
     */
    AgreeUserLoginRequestHandler("agreeUserLoginRequestHandler",
            "赞同登录请求handler", RESOURCE_SCOPE_AGREE),

    /**
     * 1.2 客户端用户登录通知
     * <p>userlogin_notify</p>
     */
    AgreeLoginNotifyHandler("agreeLoginNotifyHandler",
            "赞同客户端用户登录通知handler", RESOURCE_SCOPE_AGREE),

    /**
     * 1.3 进房间验证通知
     * <p>prepare_enterroom</p>
     */
    AgreePrepareEnterRoomHandler("agreePrepareEnterRoomHandler",
            "赞同进房间验证通知handler", RESOURCE_SCOPE_AGREE),

    /**
     * 1.4 进出房间通知
     * <p>room_notify</p>
     */
    AgreeRoomNotifyHandler("agreeRoomNotifyHandler", "赞同进出房间通知handler", RESOURCE_SCOPE_AGREE),

    /**
     * 1.5 透明通道数据通知
     * <p>transbuffer_notify</p>
     */
    AgreeSendOnlineMessageHandler("agreeSendOnlineMessageHandler",
            "赞同透明通道数据通知handler", RESOURCE_SCOPE_AGREE),

    /**
     * 1.7 录像或拍照结束通知
     * <p>recordsnapshot_notify</p>
     */
    AgreeRecordSnapShotNotifyHandler("agreeRecordSnapShotNotifyHandler",
            "赞同录像或拍照结束通知handler", RESOURCE_SCOPE_AGREE),

    /**
     * 1.8 客户端用户签退通知
     * <p>userlogout_notify</p>
     */
    AgreeUserLogoutNotifyHandler("agreeUserLogoutNotifyHandler",
            "赞同客户端用户签退通知handler", RESOURCE_SCOPE_AGREE),


    /// 赞同 end


    /**
     * 西亚mongoHandler
     */
//    TheaMongoHandler("theaMongoHandler", "西亚mongoHandler"),

    /**
     * 客服话单事件
     */
    AcdTicketEventMongoHandler("acdTicketEventMongoHandler",
            "客服话单事件mongodb入库Handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 客服事件mongodb入库handler
     */
    AcdEventMongoHandler("acdEventMongoHandler",
            "客服事件mongodb入库handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * log日志mongodb入库handler
     */
    LogMongoHandler("logMongoHandler", "日志mongodb入库handler", RESOURCE_SCOPE_GLOBAL),
    /**
     * info记录mongodb入库handler
     */
    InfoMongoHandler("infoMongoHandler", "info记录mongodb入库handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 录制事件mongodb入库handler
     */
    AcdRecordEventMongoHandler("acdRecordEventMongoHandler",
            "录制事件mongodb入库handler", RESOURCE_SCOPE_B03),

    /**
     * 终端埋点事件mongodb入库handler
     */
    MdEventMongoHandler("mdEventMongoHandler",
            "终端埋点事件mongodb入库handler", RESOURCE_SCOPE_B03),


    /**
     * concurrentMongo记录mongodb入库handler
     */
    ConcurrentMongoHandler("concurrentMongoHandler", "concurrentMongo记录mongodb入库handler", RESOURCE_SCOPE_GLOBAL),
    /**
     * concurrentMongo记录mongodb入库handler
     */
    ConcurrentStaffMongoHandler("concurrentStaffMongoHandler", "concurrentStaffMongo记录mongodb入库handler", RESOURCE_SCOPE_GLOBAL),
    /**
     * concurrentMongo记录mongodb入库handler
     */
    ConcurrentQueueMongoHandler("concurrentQueueMongoHandler", "concurrentQueueMongo记录mongodb入库handler", RESOURCE_SCOPE_GLOBAL),

    /**
     * 队列状态kafka处理handler
     */
    QueueStatusKafkaHandler("queueStatusKafkaHandler",
            "队列状态kafka事件Handler", RESOURCE_SCOPE_B03),
    /**
     * 坐席状态kafka处理handler
     */
    StaffStatusKafkaHandler("staffStatusKafkaHandler", "坐席状态kafka事件Handler", RESOURCE_SCOPE_B03),
    /**
     * 话单kafka处理handler
     */
    TicketKafkaHandler("ticketKafkaHandler", "话单kafka事件Handler", RESOURCE_SCOPE_B03),
    /**
     * 坐席状态redis处理handler
     */
    StaffRedisHandler("staffRedisHandler", "坐席状态redis处理handler", RESOURCE_SCOPE_B03),
    /**
     * 并发redis处理handler
     */
    ConcurrentRedisHandler("concurrentRedisHandler", "并发redis处理handler", RESOURCE_SCOPE_GLOBAL),
    /**
     * 删除坐席状态redis处理handler
     */
    StaffRemoveRedisHandler("staffRemoveRedisHandler", "删除坐席状态redis处理handler", RESOURCE_SCOPE_B03),
    /**
     * 队列排队redis处理handler
     */
    QueueWaitRedisHandler("queueWaitRedisHandler", "队列排队redis处理handler", RESOURCE_SCOPE_B03),
    /**
     * 队列通话redis处理handler
     */
    QueueCallRedisHandler("queueCallRedisHandler", "队列通话redis处理handler", RESOURCE_SCOPE_B03),
    /**
     * 队列振铃redis处理handler
     */
    QueueRingRedisHandler("queueRingRedisHandler", "队列振铃redis处理handler", RESOURCE_SCOPE_GLOBAL),


    /**
     * 内部处理handler
     */
    FIRST("innerFirstHandler", "内部处理handlerFirst", RESOURCE_SCOPE_INNER),

    /**
     * 内部处理handler
     */
    LAST("innerLastHandler", "内部处理handlerLast", RESOURCE_SCOPE_INNER);

    /**
     * 类型
     */
    private String name;

    /**
     * 间隔
     */
    private String id;

    /**
     * 作用域
     */
    private String scope;

    HandlerId(String id, String name, String scope) {
        this.id = id;
        this.name = name;
        this.scope = scope;
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
     * 获取 handlerID
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return 作用域
     */
    public String getScope() {
        return scope;
    }

}
