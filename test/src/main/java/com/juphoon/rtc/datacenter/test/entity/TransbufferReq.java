package com.juphoon.rtc.datacenter.test.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>在开始处详细描述作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ke.wang@juphoon.com
 * @date   2022/3/25
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]       
 */
@Data
public class TransbufferReq {

    private String appguid;

    private String userid;

    private String username;

    private String datesize;

    private String databuf;

    public Map<String,Object> convertToMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("appguid",this.getAppguid());
        map.put("userid",this.getUserid());
        map.put("username",this.getUsername());
        map.put("datesize",this.getDatesize());
        map.put("databuf",this.getDatabuf());
        return map;
    }

}
