package com.juphoon.rtc.datacenter.binlog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/9 14:46
 * @Description:
 */
@Getter
@Setter
public class EventBinLogPO {
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
    private Integer number;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 其他参数
     */
    private String params;

    /**
     * 通过 EventContext 构造
     *
     * @param ec
     * @return
     */
    @SneakyThrows
    public static EventBinLogPO fromEventContext(EventContext ec) {
        assert null != ec : "ec 不能为空";

        EventBinLogPO po = new EventBinLogPO();

        po.setId(ec.getId());
        po.setRequestId(ec.getRequestId());
        po.setFrom(ec.getFrom());
        po.setProcessorId(ec.getProcessorId());
        po.setReceivedTimestamp(ec.getCreatedTimestamp());
        po.setRedoHandler(ec.getRedoHandler());
        po.setRetryCount(ec.getRetryCount());

        po.setDomainId(ec.getEvent().getDomainId());
        po.setAppId(ec.getEvent().getAppId());
        po.setUuid(ec.getEvent().getUuid());
        po.setType(ec.getEvent().getType());
        po.setNumber(ec.getEvent().getNumber());
        po.setTimestamp(ec.getEvent().getTimestamp());

        String json = new ObjectMapper().writeValueAsString(ec.getEvent().getParams());
        po.setParams(json);

        return po;
    }

    @SneakyThrows
    public static EventContext toEventContext(EventBinLogPO po) {
        EventContext ec = new EventContext();

        ec.setId(po.getId());
        ec.setRequestId(po.getRequestId());
        ec.setFrom(po.getFrom());
        ec.setProcessorId(po.getProcessorId());
        ec.setCreatedTimestamp(po.getReceivedTimestamp());
        ec.setRedoHandler(po.getRedoHandler());
        ec.setRetryCount(po.getRetryCount());

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        Map<String, Object> params = mapper.readValue(po.getParams(), typeRef);

        Event event = Event.builder()
                .domainId(po.getDomainId())
                .appId(po.getAppId())
                .type(po.getType())
                .number(po.getNumber())
                .timestamp(po.getTimestamp())
                .params(params)
                .uuid(po.getUuid())
                .build();

        ec.setEvent(event);

        return ec;
    }

    public String dump() {
        return new StringJoiner(", ", EventBinLogPO.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
//                .add("contextId='" + contextId + "'")
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
                .add("number=" + number)
                .add("timestamp=" + timestamp)
                .add("params='" + params + "'")
                .toString();
    }
}
