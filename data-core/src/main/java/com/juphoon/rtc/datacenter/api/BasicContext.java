package com.juphoon.rtc.datacenter.api;

import com.juphoon.rtc.datacenter.utils.JrtcIdGenerator;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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

    public abstract EventType getEventType();

    @Override
    public String toString() {
        return requestId;
    }
}
