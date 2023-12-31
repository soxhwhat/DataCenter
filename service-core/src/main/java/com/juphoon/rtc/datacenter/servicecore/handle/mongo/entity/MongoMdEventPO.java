package com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity.MongoEventPO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * <p>录制埋点事件po</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @date 7/14/22 7:26 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Document("jrtc.md.events_#{new java.text.SimpleDateFormat(\"yyyyMMdd\").format(new java.util.Date())}")
@CompoundIndexes({
        @CompoundIndex(name = "event_type_num_idx",
                def = "{'type':1,'number':1}"),
        @CompoundIndex(name = "event_domain_app_idx",
                def = "{'domainId':1,'appId':1}")
})
@Getter
@Setter
public class MongoMdEventPO extends MongoEventPO {
    /**
     * 单通通话有一个唯一ID   关联ID
     */
    @Indexed
    private String traceId;
    /**
     * 平台
     */
    private String platform;
    /**
     * 渠道
     */
    private String channel;
    /**
     * sdk版本
     */
    private String sdkVersion;
    /**
     * 浏览器信息
     */
    private String browser;
    /**
     * 登录用户
     */
    private String username;
    /**
     * 业务号
     */
    private String businessId;
    /**
     * 话单ID
     */
    @Indexed
    private String callId;
    /**
     * 应用appKey
     */
    private String appKey;
    /**
     * params中的domainId
     */
    private String paramsDomainId;
    /**
     * params中的appId
     */
    private String paramsAppId;
    /**
     * 诊断事件类型
     */
    private Integer paramsType;
    /**
     * 行为事件执行结果
     */
    private Integer paramsResult;
    /**
     * 详情
     */
    private String content;
    /**
     * CD账号
     */
    private String cdAccount;
    /**
     * sip话单id
     */
    private String sipCallId;
    /**
     * 文件名
     */
    private String filename;

    /**
     * 录制时长与通话时长的差值
     */
    private Integer diffRecordTime;

    /**
     * 文件大小
     */
    private Integer fileSize;

    /**
     * 录制时长
     */
    private Integer recordTime;

    /**
     * RoomId
     */
    private String roomId;

    /**
     * 创建者
     */
    private String actor;

    /**
     * 销毁原因
     */
    private String reason;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 随路数据
     */
    private String moreInfo;

    /**
     * 是否支持赞同
     */
    private String agreeEnabled;

    /**
     * 设备类型
     */
    private Integer deviceType;




    @Override
    public void fromEvent(Event event) {
        super.fromEvent(event);
        Map<String, Object> params = this.getParams();
        this.setTraceId(this.getStringValue(params, "traceId"));
        this.setPlatform(this.getStringValue(params, "platform"));
        this.setChannel(this.getStringValue(params, "channel"));
        this.setSdkVersion(this.getStringValue(params, "sdkVersion"));
        this.setBrowser(this.getStringValue(params, "browser"));
        this.setUsername(this.getStringValue(params, "username"));
        this.setBusinessId(this.getStringValue(params, "businessId"));
        this.setCallId(this.getStringValue(params, "callId"));
        this.setAppKey(this.getStringValue(params, "appKey"));
        this.setParamsType(this.getIntegerValue(params, "type"));
        this.setParamsResult(this.getIntegerValue(params, "result"));
        this.setParamsDomainId(this.getStringValue(params, "domainId"));
        this.setParamsAppId(this.getStringValue(params, "appId"));
        this.setContent(this.getStringValue(params, "content"));
        this.setCdAccount(this.getStringValue(params, "cdAccount"));
        this.setSipCallId(this.getStringValue(params, "sipCallId"));
        this.setFilename(this.getStringValue(params, "filename"));
        this.setDiffRecordTime(this.getIntegerValue(params, "diffRecordTime"));
        this.setFileSize(this.getIntegerValue(params, "fileSize"));
        this.setRecordTime(this.getIntegerValue(params, "recordTime"));
        this.setParams(null);
        this.setRoomId(this.getStringValue(params, "roomId"));
        this.setActor(this.getStringValue(params, "actor"));
        this.setReason(this.getStringValue(params, "reason"));
        this.setUserId(this.getStringValue(params, "userId"));
        this.setMoreInfo(this.getStringValue(params, "moreInfo"));
        this.setAgreeEnabled(this.getStringValue(params, "agreeEnabled"));
        this.setDeviceType(this.getIntegerValue(params, "type"));
    }

    private String getStringValue(Map<String, Object> params, String key) {
        Object value = params.get(key);
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    private Integer getIntegerValue(Map<String, Object> params, String key) {
        String value = getStringValue(params, key);
        if (value != null) {
            return Integer.valueOf(value);
        }
        return null;
    }

}

