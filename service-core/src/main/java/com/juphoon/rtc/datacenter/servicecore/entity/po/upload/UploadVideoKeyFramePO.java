package com.juphoon.rtc.datacenter.servicecore.entity.po.upload;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * <p打点数据实体</p>
 *
 * @author  ke.wang@juphoon.com
 * @date   2022/7/28
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Data
@TableName("upload_video_key_frame")
public class UploadVideoKeyFramePO {

    @TableId(type = IdType.AUTO)
    private String id;


    private String videoId;
    /**
     * 时间偏移量
     */
    private Long timeOffset;
    /**
     * 打点类型，目前仅支持text
     */
    private String keyFrameType;
    /**
     * 打点内容，数据库中应当是512长度
     */
    private String keyFrameData;

}
