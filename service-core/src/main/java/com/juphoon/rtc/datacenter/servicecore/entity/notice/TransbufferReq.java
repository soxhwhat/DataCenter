package com.juphoon.rtc.datacenter.servicecore.entity.notice;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rongbin.huang
 * @create 2019-12-18 10:21 AM
 **/
@Data
public class TransbufferReq {

    private String appguid;

    private String userid;

    private String username;

    private String datesize;

    private String databuf;

    public Map<String, String> convertToMap() {
        HashMap<String, String> map = new HashMap<>(5);

        map.put("appguid", this.getAppguid());
        map.put("userid", this.getUserid());
        map.put("username", this.getUsername());
        map.put("datesize", this.getDatesize());
        map.put("databuf", this.getDatabuf());

        return map;
    }

}
