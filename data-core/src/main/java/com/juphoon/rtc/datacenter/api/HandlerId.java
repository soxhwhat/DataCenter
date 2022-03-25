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
     * 赞同登录通知handler
     */
    AgreeLoginNotifyHandler("agreeLoginNotifyHandler", "赞同登录通知handler"),

    /**
     * 赞同登出通知handler
     */
    AgreeLogoutNotifyHandler("agreeLogoutNotifyHandler", "赞同登出通知handler"),

    /**
     * 赞同登录请求handler
     */
    AgreeUserLoginRequestHandler("agreeUserLoginRequestHandler", "赞同登录请求handler"),

    /**
     * 西亚mongoHandler
     */
    TheaMongoHandler("theaMongoHandler", "西亚mongoHandler"),

    /**
     * mongo事件Handler
     */
    EventMongoHandler("eventMongoHandler", "mongo事件Handler"),

    /**
     *
     */
    EventKafkaHandler("eventKafkaHandler", "kafka事件Handler"),


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
