/**
 * Copyright (C), 2005-2020, Juphoon Corporation
 * <p>
 * FileName   : FlowTicket
 * Author     : wenqiangdong
 * Date       : 2020/7/31 2:29 下午
 * Description:
 * <p>
 * <p>
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.juphoon.rtc.datacenter.entity;

import lombok.Data;
import org.bson.Document;

/**
 * 〈〉
 *  此数据类型可能需要存储在redis
 * @author wenqiangdong
 * @date 2020/7/31
 */
@Data
public class FlowTicket {

    private Long updateTime;
    private Integer status;
    private String uniqueId;
    /**
     * 需要根据type不同,存入不同的表。
     */
    private Integer type;
    private Document params;
    private String domainId;
    private String appId;
}
