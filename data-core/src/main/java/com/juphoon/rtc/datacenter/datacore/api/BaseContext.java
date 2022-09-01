package com.juphoon.rtc.datacenter.datacore.api;


import com.juphoon.rtc.datacenter.datacore.utils.JrtcIdGenerator;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Getter
@Setter
public abstract class BaseContext {

    /**
     * context唯一id
     * <b>redo时需要重新生成，一定要注意</b>
     */
    private Long id = JrtcIdGenerator.newId();

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
     * 重做handler
     * <b>每次重做都需要重新生成</b>
     */
    private String redoHandler;

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

    /**
     * 重做成功
     */
    public void redoOK() {
        redoHandler = null;
        retryCount = 0;
    }

    /**
     * 获取事件类型枚举值
     *
     * @return
     */
    public abstract EventType getEventType();

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public String dump() {
        return new StringJoiner(", ")
                .add("id=" + id)
                .add("requestId='" + requestId + "'")
                .add("from='" + from + "'")
                .add("processorId='" + processorId + "'")
                .add("createdTimestamp=" + createdTimestamp)
                .add("beginTimestamp=" + beginTimestamp)
                .add("redoHandler='" + redoHandler + "'")
                .add("retryCount=" + retryCount)
                .toString();
    }
}
