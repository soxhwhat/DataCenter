/**
 * Copyright (C), 2005-2020, Juphoon Corporation
 * <p>
 * FileName   : ImgFileInfo
 * Author     : wenqiangdong
 * Date       : 2020/9/14 9:08 上午
 * Description:
 * <p>
 * <p>
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.juphoon.rtc.datacenter.servicecore.entity.po.upload;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>上传的图片信息</p>
 *
 * @author  ke.wang@juphoon.com
 * @date   2022/7/28
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@TableName("upload_image_info")
public class UploadImageInfoPO {

    @TableId(type = IdType.AUTO)
    private String id;

    private Integer domainId;

    private Integer appId;
    /**
     * 通话ID
     */
    private String callId;

    /**
     * 拍照时间
     */
    private Long takeTime;

    /**
     * 桶名',
     */
    private String bucketName;
    /**
     * 文件名
     */
    private String fileName;

    private Long fileSize;

    private String fileHash;
    private String filePath;

    /**
     * s3url
     */
    private String url;
    /**
     * 上传的IP
     */
    private String uploadHost;
    private Long uploadTime;
    private Integer fileFrom;
    /**
     * info文件中的额外信息
     */
    private String callParams;

    /**
     * 调用上传时所传递的参数
     */
    private String callUploadParams;
    /**
     * 业务流水号
     */
    private String busiSerialNo;
}
