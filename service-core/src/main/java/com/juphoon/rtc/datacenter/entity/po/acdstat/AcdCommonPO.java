package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Date;

/**
 * 汇总通用字段
 *
 * @author Yuan
 */
@Getter
@ToString
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
        justification = "I prefer to suppress these FindBugs warnings")
public class AcdCommonPO implements Serializable {

    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 记录创建时间(业务无关)
     */
    private Date gmtCreated;

    /**
     * 记录最后修改时间(业务无关)
     */
    private Date gmtModified;

    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 汇总时间
     */
    private Long statTime;

    /**
     * 事件类型
     */
    private Integer eventType;

    /**
     * 事件编号
     */
    private Integer eventNum;

    /**
     * 事件结束类型
     */
    private Integer endType;

    /**
     * 事件总时长，ms
     */
    private Long duration;

    /**
     * 事件总次数
     */
    private Integer cnt = 1;

    /**
     * 计算出的值，确定一条数据
     */
    private String uniqueKey;

    public void setId(String id) {
        this.id = id;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public void setDomainId(int domainId) {
        checkParam(domainId, "domainId");
        this.domainId = domainId;
    }

    public void setAppId(int appId) {
        checkParam(appId, "appId");
        this.appId = appId;
    }

    public void setStatTime(Long statTime) {
        this.statTime = statTime;
    }

    public void setEventType(Integer eventType) {
        checkParam(eventType, "eventType");
        this.eventType = eventType;
    }

    public void setEventNum(Integer eventNum) {
        checkParam(eventNum, "eventNum");
        this.eventNum = eventNum;
    }

    public void setEndType(Integer endType) {
        checkParam(endType, "endType");
        this.endType = endType;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    private void checkParam(Integer value, String fieldName) {
        if (value == null || value < 0) {
            throwException(fieldName + "[" + value + "] is null or < 0");
        }
    }

    protected void throwException(String message) {
        throw new InvalidParameterException(message);
    }
}