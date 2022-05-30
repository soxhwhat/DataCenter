package com.juphoon.rtc.datacenter.constant;

import com.juphoon.iron.cube.starter.log.ServiceEvent;
import org.slf4j.event.Level;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/5/18 17:18
 * @Description:
 */
public class JrtcDataCenterEventCode {

    /**
     * NoticeEventService
     */
    public final static ServiceEvent E_VER_CODE_ERROR = new ServiceEvent(Level.WARN, 720001, "RPC接口verCode_begin调用失败");
    public final static ServiceEvent E_VER_JOIN_ROOM_ERROR = new ServiceEvent(Level.WARN, 720002, "RPC接口verJoinRoom_begin调用失败");
    public final static ServiceEvent E_ROOM_NOTICE_ERROR = new ServiceEvent(Level.WARN, 720003, "RPC接口roomNotice_begin调用失败");
    public final static ServiceEvent E_RECORD_SNAPSHOT_NOTICE_ERROR = new ServiceEvent(Level.WARN, 720004, "RPC接口recordSnapshotNotice_begin调用失败");
    public final static ServiceEvent E_SEND_ONLINE_MESSAGE_AND_NOTICE_ERROR = new ServiceEvent(Level.WARN, 720005, "RPC接口sendOnlineMessageAndNotice_begin调用失败");
    public final static ServiceEvent E_KEEPALIVE_ERROR = new ServiceEvent(Level.WARN, 720006, "RPC接口keepAlive_begin调用失败");

    /**
     * event,log,status rpc接口
     */
    public final static ServiceEvent E_LOG_ERROR = new ServiceEvent(Level.WARN, 720007, "RPC接口log_begin调用失败");
    public final static ServiceEvent E_JSON_ERROR = new ServiceEvent(Level.WARN, 720008, "JSON转换异常");
    public final static ServiceEvent E_STATUS_ERROR = new ServiceEvent(Level.WARN, 720009, "RPC接口status调用失败");

    /**
     * 框架日志
     */
    public final static ServiceEvent E_PROCESS_SUBMIT_ERROR = new ServiceEvent(Level.WARN, 720010, "process入队失败");
    public final static ServiceEvent E_PROCESS_SUBMIT_SAVE_ERROR = new ServiceEvent(Level.WARN, 720011, "process入队记录失败");
    public final static ServiceEvent E_HANDLER_EXCEPTION = new ServiceEvent(Level.WARN, 720012, "handle执行异常");
    public final static ServiceEvent E_REDO_HANDLER_EXCEPTION = new ServiceEvent(Level.WARN, 720013, "handle重做异常");
    public final static ServiceEvent E_PUSH_QUEUE_ERROR = new ServiceEvent(Level.WARN, 720014, "push内存队列失败");

    /**
     * 业务日志
     */
    public final static ServiceEvent E_MONGO_HANDLE_ERROR = new ServiceEvent(Level.ERROR, 720015, "mongo处理器执行错误");
    public final static ServiceEvent E_REDIS_HANDLE_ERROR = new ServiceEvent(Level.ERROR, 720016, "redis处理器执行错误");
    public final static ServiceEvent E_DATA_BASE_ERROR = new ServiceEvent(Level.ERROR, 720017, "数据库操作执行错误");
    public final static ServiceEvent E_KAFKA_HANDLE_ERROR = new ServiceEvent(Level.ERROR, 720018, "kafka处理器执行错误");


}
