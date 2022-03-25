package com.juphoon.rtc.datacenter.entity.po.acdstat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 坐席签入签出日表
 *
 * @author Yuan
 */
@Getter
@Setter
@ToString
@TableName("jrtc_acd_agentop_sign_daily")
public class AcdAgentOpSignDailyPO {

    @TableId(type = IdType.AUTO)
    private String id;

    private Date gmtCreated;

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
     * 事件触发成员
     */
    private String agentId;

    /**
     * 班次(默认为日期)
     */
    private String shift;

    /**
     * 班组(可选)
     */
    private String team = "";

    /**
     * 首次登录时间戳(毫秒)
     */
    private Long firstLogin;

    /**
     * 最后登出时间戳(毫秒)
     */
    private Long lastLogout;

    /**
     * 首次签入时间戳(毫秒)
     */
    private Long firstSignIn;

    /**
     * 最后签出时间戳(毫秒)
     */
    private Long lastSignOut;

    /**
     * 计算出的值，确定一条数据
     */
    private String uniqueKey;


}