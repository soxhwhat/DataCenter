package com.juphoon.rtc.datacenter.dist.h20.entity;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import lombok.Data;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Map;

/**
 * <p>话单  kafka通知  PO</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/7/26 0:20
 */
@Data
public class KafkaTicketPo implements Serializable {
    /**
     * 主叫
     */
    private String from;
    /**
     * 被叫
     */
    private String to;
    private String agentId;
    private String callId;
    /**
     * 集团ID
     */
    private String groupId;
    /**
     * 计费号
     */
    private String e55;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 通话时长
     */
    private String duration;
    /**
     * |||语音时长,480P时长,720P时长,1080P时长 单位 秒
     */
    private String detail;

    public void fromEvent(Event event) {
        Map<String, Object> params = event.getParams();
        this.setFrom(this.getStringValue(params, "caller"));
        this.setTo(this.getStringValue(params, "callee"));
        this.setAgentId(this.getStringValue(params, "agentId"));
        this.setCallId(this.getStringValue(params, "callId"));
        this.setGroupId(this.getStringValue(params, "groupId"));
        this.setE55(this.getStringValue(params, "e55"));
        this.setBeginTime(Math.round(this.geLongValue(params, "beginTimestamp") / 1000.0) + "");
        this.setEndTime(Math.round(this.geLongValue(params, "endTimestamp")/1000.0) + "");
        this.setDuration(Math.round(this.geLongValue(params, "duration")/1000.0) + "");
    }

    private String getStringValue(Map<String, Object> params, String key) {
        Object value = params.get(key);
        if (value != null) {
            return value.toString();
        }
        throw new InvalidParameterException(key + " is null");
    }

    private Long geLongValue(Map<String, Object> params, String key) {
        return Long.valueOf(getStringValue(params, key));
    }

}
