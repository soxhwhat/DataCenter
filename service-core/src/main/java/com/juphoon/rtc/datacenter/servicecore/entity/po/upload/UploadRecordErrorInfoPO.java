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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


/**
 * `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 * `gmt_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 * `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 * `domain_id` int(11) DEFAULT NULL,
 * `app_id` int(11) DEFAULT NULL,
 * `record_time` int(11) DEFAULT NULL COMMENT '录制时间',
 * `call_id` varchar(255) DEFAULT NULL COMMENT '通话ID',
 * `record_agent_id` varchar(255) DEFAULT NULL COMMENT '录制代理ID',
 * `record_resource_id` varchar(255) DEFAULT NULL COMMENT '录制资源ID',
 * `bucket_name` varchar(255) DEFAULT NULL COMMENT '桶名',
 * `file_Key` varchar(255) DEFAULT NULL COMMENT '文件名',
 * `file_size` int(11) DEFAULT NULL,
 * `file_path` varchar(255) DEFAULT NULL COMMENT '文件路径',
 * `info_file_path` varchar(255) DEFAULT NULL COMMENT 'info文件路径',
 * `retry_count` int(11) DEFAULT 0 COMMENT '已重试次数',
 * `status` int(11) DEFAULT 1 COMMENT '状态 0:重传成功,1:上传失败,2:过期清理',
 * `upload_host` varchar(20) DEFAULT NULL COMMENT '上传服务器',
 * `fail_reason` varchar(512) DEFAULT NULL COMMENT '上一回失败原因',
 * 〈〉
 * 上传失败的文件记录
 *
 * @author ke.wang@juphoon.com
 * @date 2022/7/28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("upload_record_error_info")
public class UploadRecordErrorInfoPO {

    @TableId(type = IdType.AUTO)
    private String id;
    private Integer domainId;
    private Integer appId;

    private String callId;
    private String recordAgentId;
    private String recordResourceId;

    private Long recordTime;
    private Long recordBeginTimestamp;
    private Long recordEndTimestamp;

    private String bucketName;
    private String fileName;
    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件哈希
     */
    private String fileHash;
    private String filePath;
    private String infoFilePath;
    /**
     * 重试次数
     */
    private Integer retryCount;
    /**
     * 状态， 0:重传成功,1:上传失败,2:过期清理
     */
    private Integer status;


    private Long uploadTime;


    /**
     * 上传服务器
     */
    private String uploadHost;


    /**
     * 最后一次失败原因
     */
    private String failReason;


    private String oid;

    /**
     * 随路数据
     */
    private String moreInfo;
    /**
     * 调用参数
     */
    private String callParams;

    public String busiSerialNo;
}
