package com.juphoon.rtc.datacenter.api;

import com.juphoon.rtc.datacenter.handler.IHandler;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Getter
@Setter
public abstract class BasicContext {
    /**
     * rpc magic
     */
    @NotNull
    private String requestId;

    /**
     * 来源
     */
    private String from;

    /**
     * 处理器id, 方便后续从数据库load出来后dispatch
     */
    private String processorId;

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
        // TODO, 消息唯一ID
        // TODO, 消息唯一ID
        // TODO, 消息唯一ID
        // TODO, 消息唯一ID
        // TODO, 消息唯一ID
        // TODO, 消息唯一ID
        assert null != processorId : "processorId 不能为空";

        return requestId + processorId;
    }

    public abstract EventType getEventType();

    /**
     * 是否重做OK
     * 1. 是重做消息
     * 2. 重做handler列表为空
     *
     * @return
     */
    public <T extends BasicContext> boolean redoOk(IHandler<T> handler) {
        return isRedoEvent() && null != redoHandlerIds && redoHandlerIds.isEmpty();
    }

    /**
     * 清理handler重做标记
     * @param handler
     */
    public  <T extends BasicContext> void cleanRedoFlag(IHandler<T> handler) {
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
}
