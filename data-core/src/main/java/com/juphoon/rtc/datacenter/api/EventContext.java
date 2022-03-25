package com.juphoon.rtc.datacenter.api;

import com.juphoon.rtc.datacenter.handler.IEventHandler;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Getter
@Setter
public class EventContext {

    private String magic;

    private String from;

    /**
     * rpc magic
     */
    @NotNull
    private String requestId;

    /**
     * 处理器id, 方便后续从数据库load出来后dispatch
     */
    private String processorId;

    /**
     * 消息体
     */
    @NotNull
    private Event event;

    /**
     * 创建时间
     */
    private long createdTimestamp = System.currentTimeMillis();

    /**
     * 首次消费时间
     */
    private long beginTimestamp = 0;

    /**
     * 重做handler列表
     */
    private List<String> redoHandlerIds;

    /**
     * 重做次数
     */
    private int retryCount = -1;

    /**
     * 是否入队，有可能队列满入队失败，该标记可辅助扫描线程从db中扫描此类事件，重新load到队列中
     */
    private boolean queued = true;

    /**
     * 处理
     */
    public void handle() {
        retryCount++;
        beginTimestamp = System.currentTimeMillis();
    }

    /**
     * 是否为重做事件
     *
     * @return
     */
    public boolean isRedoEvent() {
        return retryCount > 0;
    }

    public String getId() {
        assert null != processorId : "processorId 不能为空";

        return requestId + processorId;
    }

    /**
     * 是否重做OK
     * 1. 是重做消息
     * 2. 重做handler列表为空
     *
     * @return
     */
    public boolean redoOk(IEventHandler handler) {
        return isRedoEvent() && null != redoHandlerIds && redoHandlerIds.isEmpty();
    }

    /**
     * 清理handler重做标记
     * @param handler
     */
    public void cleanRedoFlag(IEventHandler handler) {
        if (null == redoHandlerIds) {
            return;
        }

        redoHandlerIds.remove(handler.getId());
    }

    /**
     * 消息是否处理OK
     * 1. 重做handler列表为空
     * @return
     */
    public boolean processOk() {
        return null == redoHandlerIds || redoHandlerIds.isEmpty();
    }

    @Override
    public String toString() {
        return requestId;
    }

    /**
     * 打印详情
     * // TODO 优化
     *
     * @return
     */
    public String body() {
        return new StringJoiner(", ", EventContext.class.getSimpleName() + "[", "]")
                .add("from='" + from + "'")
                .add("requestId='" + requestId + "'")
                .add("processorId='" + processorId + "'")
                .add("event=" + event)
                .add("createdTimestamp=" + createdTimestamp)
                .add("beginTimestamp=" + beginTimestamp)
                .add("redoHandlerIds=" + redoHandlerIds)
                .add("retryCount=" + retryCount)
                .add("queued=" + queued)
                .toString();
    }
}
