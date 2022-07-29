package com.juphoon.rtc.datacenter.servicecore.entity.po.upload;

import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe.KeyFrameData;
import lombok.Data;

@Data
public class VideoKeyFrameEntity<T extends KeyFrameData> {

    private String id;
    /**
     * 时间偏移量
     */
    private Long timeOffset;
    private String videoId;
    /**
     * 打点类型，目前仅支持text
     */
    private String type;
    /**
     * 打点内容，数据库中应当是512长度
     */
    private T data;
}
