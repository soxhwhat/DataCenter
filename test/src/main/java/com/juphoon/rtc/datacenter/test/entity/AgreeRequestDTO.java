package com.juphoon.rtc.datacenter.test.entity;

import lombok.Data;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ke.wang@juphoon.com
 * @date    2022/3/25 16:55
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */ 
@Data
public class AgreeRequestDTO {

    private String appguid;

    private String userid;

    private String username;

    private String timestammp;

    private String signstr;

    private String strparam;

    private String roompass;

    private String roomid;

    private String param;

    private String errorcode;

    private String recordfilename;

    private String recordtime;



}
