package com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe;


import lombok.Data;

/**
 * <p>打点数据BO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/8/15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
public class VideoKeyFrameBO {

    private String id;
    /**
     * 时间偏移量
     */
    private Long timeOffset;
    private String videoId;
    /**
     * 打点类型，目前仅支持text
     */
    private String keyFrameType;
    /**
     * 打点内容，数据库中应当是512长度
     */
    private String keyFrameData;
}
