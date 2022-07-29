package com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe;

import lombok.Data;

import java.util.Map;

@Data
public class VideoKeyFrameDTO {

    private String videoId;
    /**
     * 时间偏移量
     */
    private Long timeOffset;
    /**
     * 打点类型，目前仅支持text
     */
    private String type;
    /**
     * 打点内容，数据库中应当是512长度
     */
    private Map<String, Object> data;
}
