/**
  * Copyright (C), 2005-2019, Juphoon Corporation
  *
  * FileName   : NoticeProperties
  * Author     : wenqiangdong
  * Date       : 2019-12-16 17:55
  * Description: 
  * 
  *
  * History:
  * <author>          <time>          <version>          <desc>
  * 作者姓名           修改时间           版本号              描述
  */
    package com.juphoon.rtc.datacenter.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author wenqiangdong
 * @date   2019-12-16
 */
@Data
@ConfigurationProperties(prefix = "notice.config")
@Component
public class NoticeProperties {

    /**
     * 调用地址
     */
    private String host = "http://localhost:8080";

    /**
     * 应用服务ID
     */
    private String appguid = "";

    private String appId;

    /**
     * im消息最大长度
     */
    private Integer imTextMacLength = 1000;


    private Integer keepAliveSeconds = 30;


}
