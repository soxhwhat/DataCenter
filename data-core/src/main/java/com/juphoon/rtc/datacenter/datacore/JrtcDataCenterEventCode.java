package com.juphoon.rtc.datacenter.datacore;

import com.juphoon.iron.cube.starter.rccode.ServiceEvent;
import org.slf4j.event.Level;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/5/18 17:18
 * @Description:
 */
public class JrtcDataCenterEventCode {
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
