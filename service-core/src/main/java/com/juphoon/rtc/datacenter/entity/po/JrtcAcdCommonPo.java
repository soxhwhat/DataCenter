package com.juphoon.rtc.datacenter.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Yuan
 */
@Getter
@Setter
@ToString
public class JrtcAcdCommonPo {

    @TableId(type = IdType.AUTO)
    private String id;

    private Date gmtCreated;

    private Date gmtModitified;

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
    private Integer cnt;

    /**
     * 计算出的值，确定一条数据
     */
    private Integer uniqueHashCode;
}