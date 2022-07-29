package com.juphoon.rtc.datacenter.datacore.api;

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
     * 坐席登录
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
     * 队列排队状态同步/心跳
     * @see com.juphoon.rtc.datacenter.handle.database.monitor.MonitorAcdQueueHandler
     */
    QUEUE_WAIT_BEAT(13, 0),
    /**
     * 队列振铃状态同步/心跳
     */
    QUEUE_RING_BEAT(13, 1),
    /**
     * 队列通话状态同步/心跳
     */
    QUEUE_CALL_BEAT(13, 2),

    //***************************************************************************************************
    //**********************************   日志事件  ***************************************************
    //***************************************************************************************************

    /**
     * 日志收集事件
     */
    CLIENT_LOG_EVENT(66,0),
    SERVER_LOG_EVENT(66,1),

    /**
     * log info
     */
    CLIENT_LOG_INFO_EVENT(66,2),

    //***************************************************************************************************
    //**********************************   日志事件  ***************************************************
    //***************************************************************************************************
//
//    /**
//     * info收集事件
//     */
//    INFO_EVENT(67,0),

    //***************************************************************************************************
    //**********************************   并发统计  ***************************************************
    //***************************************************************************************************

    /**
     * 并发统计
     */
    ROOM_BEAT(22,0),

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


//***************************************************************************************************
    //**********************************   终端埋点录制事件  ***************************************************
    //***************************************************************************************************
    /**
     * 登录成功
     */
    MD_LOGIN_SUCCESS(1000,0),

    /**
     * 登录失败
     */
    MD_LOGIN_ERROR(1000,1),

    /**
     * 呼叫成功
     */
    MD_CALL_SUCCESS(1000,2),

    /**
     * 呼叫失败
     */
    MD_CALL_ERROR(1000,3),

    /**
     * 通话建立
     */
    MD_CALL_ESTABLISHED(1000,4),

    /**
     * 通话异常结束
     */
    MD_CALL_EXP_END(1000,5),

    /**
     * 异常结束
     */
    MD_RECORD_ERROR(1000,6),

    /**
     * 麦克风权限异常
     */
    MD_MICROPHONE_PERMISSION_ERROR(1000,7),

    /**
     * 摄像头权限异常
     */
    MD_CAMERA_PERMISSION_ERROR(1000,8),

    /**
     * 音频异常
     */
    MD_AUDIO_ERROR(1000,9),

    /**
     * 渲染异常
     */
    MD_RENDER_ERROR(1000,10),

    /**
     * 浏览器上报的异常
     */
    MD_BROWSER_REPORT_ERROR(1000,11),

    /**
     * 网关连接异常
     */
    MD_GATEWAY_CONNECTION_ERROR(1000,12),

    /**
     * 媒体连接异常
     */
    MD_MEDIA_ERROR(1000,13),

//***************************************************************************************************
    //**********************************   SIP埋点录制事件  ***************************************************
    //***************************************************************************************************
    /**
     * 媒体事件
     */
    SIP_MEDIA_EVENT(1100,10),

    /**
     * Invite请求事件
     */
    SIP_INVITE_EVENT(1100,11),

    /**
     * Bye请求事件
     */
    SIP_BYE_EVENT(1100,12),

    /**
     * Ack请求事件
     */
    SIP_ACK_EVENT(1100,13),

    /**
     * Update请求事件
     */
    SIP_UPDATE_EVENT(1100,14),

    /**
     * Option请求事件
     */
    SIP_OPTION_EVENT(1100,15),

    /**
     * 其他请求事件
     */
    SIP_OTHER_REQUEST_EVENT(1100,19),

    /**
     * 100响应事件
     */
    SIP_100_EVENT(1100,20),

    /**
     * 180/183响应事件
     */
    SIP_183_EVENT(1100,21),

    /**
     * 200响应事件
     */
    SIP_200_EVENT(1100,22),

    /**
     * 3xx响应事件
     */
    SIP_300_EVENT(1100,23),

    /**
     * 4xx响应事件
     */
    SIP_400_EVENT(1100,24),

    /**
     * 5xx响应事件
     */
    SIP_500_EVENT(1100,25),

    /**
     * 6xx响应事件
     */
    SIP_600_EVENT(1100,26),

    /**
     * 其他响应事件
     */
    SIP_OTHER_RESPONSE_EVENT(1100,29),

    //***************************************************************************************************
    //**********************************   录制CD埋点事件  **********************************************
    //***************************************************************************************************
    /**
     * 加入房间
     */
    CD_JOIN_ROOM_EVENT(1200,0),

    /**
     * 收到录制开始信令
     */
    CD_RECEIVE_START_SIGNAL_EVENT(1200,1),

    /**
     * 录制开始
     */
    CD_START_EVENT(1200,2),

    /**
     * 收到录制结束信令
     */
    CD_RECEIVE_END_SIGNAL_EVENT(1200,3),

    /**
     * 录制结束
     */
    CD_END_EVENT(1200,4),

    /**
     * 录制异常
     */
    CD_ABNORMAL_EVENT(1200,5),

    /**
     * 录制心跳
     */
    CD_HEARTBEAT_EVENT(1200,6),

    /**
     * 收到第一帧媒体数据
     */
    CD_RECEIVE_FIRST_FRAME_OF_MEDIA_DATA_EVENT(1200,7),

    /**
     * 离开房间
     */
    CD_LEAVE_ROOM_EVENT(1200,8),

    //***************************************************************************************************
    //**********************************   upload埋点事件  ********************************************
    //***************************************************************************************************


    /**
     * 扫描事件
     */
    MD_SCAN_EVENT(1300, 0),

    /**
     * 上传行为
     */
    MD_UPLOAD_EVENT(1300, 1),

    /**
     * 文件移动事件
     */
    MD_FILE_MOVE_EVENT(1300, 2),

    /**
     * 文件清理事件
     */
    MD_FILE_CLEAR_EVENT(1300, 3),

    /**
     * 测试事件
     */

    TEST_EVENT(-1, -1),

    //***************************************************************************************************
    //**********************************   平台房间事件  ********************************************
    //***************************************************************************************************

    /**
     * 创建房间
     */
    ROOM_CREATE(23,1),

    /**
     * 销毁房间
     */
    ROOM_DESTROY(23,2),

    /**
     * 成员加入
     */
    ROOM_JOIN(23,99),
    /**
     * 成员离开
     */
    ROOM_LEAVE(23,98),


    //***************************************************************************************************
    //**********************************   upload 数据源事件  ********************************************
    //***************************************************************************************************

    /**
     * 插入upload_record_info
     */
    INSERT_SUCCESS_VIDEO_PO(24, 0),


    /**
     * 插入upload_image_info
     */
    INSERT_SUCCESS_IMAGE_PO(24, 1),

    /**
     * 插入upload_record_error_info
     */
    INSERT_ERROR_VIDEO_PO(24, 2),

    /**
     * 插入upload_video_key_frame
     */
    INSERT_VIDEO_KEY_FRAME_PO(24, 3),


    //***************************************************************************************************
    //**********************************   天赛全量数据上传事件  ********************************************
    //***************************************************************************************************

    THEA_MONITOR_DATA(900, 0);

    /**
     * 事件类型
     */
    private final Integer type;

    /**
     * 事件编号
     */
    private final Integer number;

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
