package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 汇总通用字段
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
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
}