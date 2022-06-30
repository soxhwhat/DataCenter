package com.juphoon.rtc.datacenter.servicecore.entity.notice;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rongbin.huang
 * @create 2019-12-20 11:50 AM
 **/
@Data
public class UserKeepAlive implements Serializable {

    private long keepAliveTime = System.currentTimeMillis();

    private String userid;

    private String username;

}
