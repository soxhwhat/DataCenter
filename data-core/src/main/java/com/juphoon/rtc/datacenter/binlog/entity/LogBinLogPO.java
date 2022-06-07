package com.juphoon.rtc.datacenter.binlog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.LogContext;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/9 14:46
 * @Description:
 */
@Getter
@Setter
public class LogBinLogPO {
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

    /**
     * 其他参数
     */
    private String logs;

    /**
     * 通过 EventContext 构造
     *
     * @param context
     * @return
     */
    @SneakyThrows
    public static LogBinLogPO fromContext(LogContext context) {
        assert null != context : "context 不能为空";

        LogBinLogPO po = new LogBinLogPO();

        po.setId(context.getId());
        po.setRequestId(context.getRequestId());
        po.setFrom(context.getFrom());
        po.setProcessorId(context.getProcessorId());
        po.setReceivedTimestamp(context.getCreatedTimestamp());
        po.setRedoHandler(context.getRedoHandler());
        po.setRetryCount(context.getRetryCount());

        String json = new ObjectMapper().writeValueAsString(context.getLogs());

        po.setLogs(json);

        return po;
    }

    @SneakyThrows
    public static LogContext toContext(LogBinLogPO po) {
        LogContext context = new LogContext();

        context.setId(po.getId());
        context.setRequestId(po.getRequestId());
        context.setFrom(po.getFrom());
        context.setProcessorId(po.getProcessorId());
        context.setCreatedTimestamp(po.getReceivedTimestamp());
        context.setRedoHandler(po.getRedoHandler());
        context.setRetryCount(po.getRetryCount());

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {
        };
        List<String> logs = mapper.readValue(po.getLogs(), typeRef);

        context.setLogs(logs);

        return context;
    }

    public String dump() {
        return new StringJoiner(", ", LogBinLogPO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("requestId='" + requestId + "'")
                .add("from='" + from + "'")
                .add("processorId='" + processorId + "'")
                .add("receivedTimestamp=" + receivedTimestamp)
                .add("redoHandler='" + redoHandler + "'")
                .add("retryCount=" + retryCount)
                .add("logs='" + logs + "'")
                .toString();
    }
}
