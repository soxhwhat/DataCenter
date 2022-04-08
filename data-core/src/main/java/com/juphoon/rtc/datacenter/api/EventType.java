package com.juphoon.rtc.datacenter.api;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 *
 * @link https://juphoon.yuque.com/videoproducts/wboebb/rc884g#1E9M
 *
 * @update 1. 2022-03-29. ajian.zheng 类改枚举
 */
public enum EventType {


    /**
     * 登录请求(赞同)
     */
    USER_LOGIN_REQUEST(25, 1),

    /**
     * 验证加入房间
     */
    VERIFY_JOIN(25, 2),

    /**
     * 房间加入/离开事件(赞同)
     */
    ROOM_NOTICE(25, 3),

    /**
     * 拍照事件
     */
    SNAPSHOT_NOTICE(25, 4),

    /**
     * 消息
     *
     * // TODO 移植到Portal
     */
    SEND_ONLINE(25, 5),

    /**
     * 登出事件
     */
    LOGOUT_EVENT(25, 6),

    /**
     * 登录事件
     */
    LOGIN_EVENT(25, 7),

    //***************************************************************************************************
    //********************************** 客服事件-排队机 ************************************************
    //***************************************************************************************************
    /**
     * 排队
     */
    TICKER_EVENT_WAIT(10, 0),
    /**
     * 振铃
     */
    TICKER_EVENT_RING(10, 1),
    /**
     * 通话
     */
    TICKER_EVENT_TALK(10, 2),
    /**
     * 溢出
     */
    TICKER_EVENT_OVERFLOW(10, 3),
    /**
     * 转接
     */
    TICKER_EVENT_TRANSFER(10, 4),
    /**
     * 邀请坐席
     */
    TICKER_EVENT_INVITE_AGENT(10, 5),

    //***************************************************************************************************
    //********************************** 客服事件-坐席 ************************************************
    //***************************************************************************************************
    /**
     * 坐席示忙
     */
    AGENT_OP_EVENT_BUSY(10, 10),
    /**
     * 坐席示闲
     */
    AGENT_OP_EVENT_FREE(10, 11),
    /**
     * 坐席保持
     */
    AGENT_OP_EVENT_KEEP(10, 12),
    /**
     * 坐席签入签出
     */
    AGENT_OP_EVENT_LOGIN(10, 13),

    /**
     * 坐席签入
     */
    AGENT_OP_EVENT_CHECK_IN(100, 13),

    /**
     * 坐席签出
     */
    AGENT_OP_EVENT_CHECK_OUT(100, 14),

    //***************************************************************************************************
    //********************************** 客服话单事件 ***************************************************
    //***************************************************************************************************
    /**
     * 话单（整个话单真正结束时）
     */
    TICKER_COMPLETE(11, 1),

    //***************************************************************************************************
    //********************************** 坐席状态/心跳 ***************************************************
    //***************************************************************************************************
    /**
     * 坐席状态/心跳
     */
    STAFF_BEAT(12, 0),

    //***************************************************************************************************
    //********************************** 队列状态/心跳 ***************************************************
    //***************************************************************************************************
    /**
     * 队列状态同步/心跳
     */
    QUEUE_BEAT(13, 0),

    //***************************************************************************************************
    //**********************************   CD录制事件  ***************************************************
    //***************************************************************************************************
    /**
     * 开始录制
     */
    RECORD_START(20, 0),
    /**
     * 停止录制
     */
    RECORD_STOP(20, 1),
    /**
     * 生成录制文件
     */
    RECORD_FILE_CREATE(20, 2),
    /**
     * CD入会成功
     */
    RECORD_JOIN_MEETING_SUCCESS(20, 3),
    /**
     * CD入会失败
     */
    RECORD_JOIN_MEETING_FAIL(20, 4),
    /**
     * CD离开
     */
    RECORD_LEAVE_MEETING(20, 5),
    /**
     * 录制错误
     */
    RECORD_ERROR(20, 6),
    /**
     * JSMS异常重启
     */
    JSMS_REBOOT(20, 7),
    /**
     * JMDS异常重启
     */
    JMDS_REBOOT(20, 8),
    /**
     * CD异常退出
     */
    CD_ERROR_EXIT(20, 9),

    /**
     * 测试事件
     */

    TEST(-1, -1);

    /**
     * 事件类型
     */
    private Integer type;

    /**
     * 事件编号
     */
    private Integer number;

    EventType(Integer type, Integer number) {
        this.type = type;
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public Integer getNumber() {
        return number;
    }
}
