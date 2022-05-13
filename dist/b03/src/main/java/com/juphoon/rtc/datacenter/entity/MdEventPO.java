package com.juphoon.rtc.datacenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * <p>埋点表</p></p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author  ke.wang@juphoon.com
 * @date    2022/5/10 10:14
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Getter
@Setter
@ToString
@TableName("t_md_events")
public class MdEventPO {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
        通话id
     */
    private String callId;

    /**
        唯一标识符
     */
    private String traceId;

    /**
        流水号
     */
    private String businessId;

    /**
        应用id
     */
    private String appKey;

    private Long eventTime;

    /**
        事件码
     */
    private Integer eventNumber;

    /**
        sdk版本号
     */
    private String sdkVersion;

    /**
      平台标识符 Android/iOS/Windows/H5/ WeChat
     */
    private String platform;

    /**
        渠道
     */
    private String channel;

    /**
        浏览器信息
     */
    private String browser;

    /**
        登录时的用户名
     */
    private String username;

    /**
        自定义json信息
     */
    private String content;



}
