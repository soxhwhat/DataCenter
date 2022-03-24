package com.juphoon.rtc.datacenter.mq.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/9 14:46
 * @Description:
 */
@TableName("t_event")
@Getter
@Setter
public class EventPO {

    /**
     * 主键ID
     */
    @TableId
    private String id;
    /**
     * 类型type
     */
    private Integer type;
    /**
     * 类型number
     */
    private Integer number;
    /**
     * 消息实体（JSON格式）
     */
    private String params;
    /**
     * 创建时间
     */
    private Long createTimestamp;
    /**
     * 开始时间
     */
    private Long beginTimestamp;
    /**
     * 状态（0准备中   1执行中）
     */
    private Integer state;
    /**
     * 重试次数
     */
    private Integer retryCount;
    /**
     * process类名
     */
    private String processClzName;
}
