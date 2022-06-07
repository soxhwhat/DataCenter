package com.juphoon.rtc.datacenter.binlog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.juphoon.rtc.datacenter.api.State;
import com.juphoon.rtc.datacenter.api.StateContext;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/9 14:46
 * @Description:
 */
@Getter
@Setter
public class StateBinLogPO {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    protected Long id;

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
    private long receivedTimestamp;

    /**
     * 重做handler
     * <b>每次重做都需要重新生成</b>
     */
    private String redoHandler;

    /**
     * 重做次数
     */
    private int retryCount = 0;

    ///// * 数据部分
    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 事件唯一ID
     */
    private String uuid;

    /**
     * 事件类型
     */
    private Integer type;

    /**
     * 事件编号
     */
    private Integer state;

    /**
     * 其他参数
     */
    private String params;

    /**
     * 通过 EventContext 构造
     *
     * @param context
     * @return
     */
    @SneakyThrows
    public static StateBinLogPO fromContext(StateContext context) {
        assert null != context : "context 不能为空";

        StateBinLogPO po = new StateBinLogPO();

        po.setId(context.getId());
        po.setRequestId(context.getRequestId());
        po.setFrom(context.getFrom());
        po.setProcessorId(context.getProcessorId());
        po.setReceivedTimestamp(context.getCreatedTimestamp());
        po.setRedoHandler(context.getRedoHandler());
        po.setRetryCount(context.getRetryCount());

        po.setDomainId(context.getState().getDomainId());
        po.setAppId(context.getState().getAppId());
        po.setUuid(context.getState().getUniqueId());
        po.setType(context.getState().getType());
        po.setState(context.getState().getType());
        po.setParams(context.getState().getParams());

        return po;
    }

    @SneakyThrows
    public static StateContext toContext(StateBinLogPO po) {
        StateContext context = new StateContext();

        context.setId(po.getId());
        context.setRequestId(po.getRequestId());
        context.setFrom(po.getFrom());
        context.setProcessorId(po.getProcessorId());
        context.setCreatedTimestamp(po.getReceivedTimestamp());
        context.setRedoHandler(po.getRedoHandler());
        context.setRetryCount(po.getRetryCount());

        State state = State.builder()
                .domainId(po.getDomainId())
                .appId(po.getAppId())
                .type(po.getType())
                .state(po.getState())
                .params(po.getParams())
                .uuid(po.getUuid())
                .build();

        context.setState(state);

        return context;
    }

    public String dump() {
        return new StringJoiner(", ", StateBinLogPO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("requestId='" + requestId + "'")
                .add("from='" + from + "'")
                .add("processorId='" + processorId + "'")
                .add("receivedTimestamp=" + receivedTimestamp)
                .add("redoHandler='" + redoHandler + "'")
                .add("retryCount=" + retryCount)
                .add("domainId=" + domainId)
                .add("appId=" + appId)
                .add("uuid='" + uuid + "'")
                .add("type=" + type)
                .add("state=" + state)
                .add("params='" + params + "'")
                .toString();
    }
}
