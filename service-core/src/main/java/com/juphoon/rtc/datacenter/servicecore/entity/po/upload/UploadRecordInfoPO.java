/**
 * Copyright (C), 2005-2019, Juphoon Corporation
 * <p>
 * FileName   : FileInfo
 * Author     : wenqiangdong
 * Date       : 2019-09-10 11:04
 * Description:
 * <p>
 * <p>
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.juphoon.rtc.datacenter.servicecore.entity.po.upload;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 〈〉
 * `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 * `gmt_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 * `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 * `domain_id` int(11) DEFAULT NULL,
 * `app_id` int(11) DEFAULT NULL,
 * `call_id` varchar(255) DEFAULT NULL COMMENT '通话ID',
 * `record_agent_id` varchar(255) DEFAULT NULL COMMENT '录制代理ID',
 * `record_resource_id` varchar(255) DEFAULT NULL COMMENT '录制资源ID',
 * `record_time` int(11) DEFAULT NULL COMMENT '录制时间',
 * `bucket_name` varchar(255) DEFAULT NULL COMMENT '桶名',
 * `file_key` varchar(255) DEFAULT NULL COMMENT '文件名',
 * `url` varchar(255) DEFAULT NULL COMMENT 's3url',
 * `file_size` int(11) DEFAULT NULL COMMENT '文件大小',
 * `file_hash` varchar(255) DEFAULT NULL ,
 * `upload_host` varchar(20) DEFAULT NULL COMMENT '上传服务器',
 * `upload_time` bigint(20) DEFAULT NULL COMMENT '上传时间',
 *
 * @author ke.wang
 * @date 2022-07-28
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("upload_record_info")
public class UploadRecordInfoPO {

    @TableId(type = IdType.AUTO)
    private String id;
    private Integer domainId;
    private Integer appId;

    private Long recordTime;

    private String callId;
    private String recordAgentId;
    private String recordResourceId;


    private Long recordBeginTimestamp;
    private Long recordEndTimestamp;

    private String bucketName;
    private String fileName;
    private String url;
    private Long fileSize;
    private String fileHash;
    private String filePath;

    private Long uploadTime;
    private Integer fileFrom;

    /**
     * 上传服务器
     */
    private String uploadHost;

    /**
     * 随路数据
     */
    private String moreInfo;
    /**
     * info文件中的额外信息
     */
    private String callParams;

    /**
     * 调用上传时所传递的参数
     */
    private String callUploadParams;
    /**
     * info文件全部内容
     */
    private String recordInfo;

    /**
     * 业务流水号
     */
    private String busiSerialNo;

    @TableField(exist = false)
    private List<VideoKeyFrameEntity<?>> videoKeyFrameList;

}
