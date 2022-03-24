package com.juphoon.rtc.datacenter.mq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/9 14:48
 * @Description:
 */
@Getter
@Setter
@TableName("t_redo")
public class RedoPO {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private String id;
    /**
     * 事件ID
     */
    private String eventId;
    /**
     * 处理器的class
     */
    private String handleClz;
    /**
     * 创建时间戳
     */
    private Long createTimeStamp;
    /**
     * 开始时间戳
     */
    private Long beginTimeStamp;
    /**
     * 重试次数
     */
    private Integer retryCount;
}
