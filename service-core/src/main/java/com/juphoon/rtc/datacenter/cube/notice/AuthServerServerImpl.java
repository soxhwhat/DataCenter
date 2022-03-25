package com.juphoon.rtc.datacenter.cube.notice;

import Common.Exception;
import Common.ServerCall;
import com.juphoon.rtc.datacenter.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 全部移植到 AuthServer 中
 * @author rongbin.huang
 * @create 2019-12-16 4:38 PM
 **/
//@Slf4j
//@Component
//public class AuthServerServerImpl extends Event.AuthServerServer {
//
//    @Autowired
//    private NoticeService noticeService;
//
//    @Override
//    public boolean authUser(ServerCall serverCall, String account, String random, String md5pwd) throws Exception {
//        boolean ret = true;
//
//        try {
//            Map<String, String> params = new HashMap<>(5);
//            params.put("userid", account);
//            params.put("username", "no");
//            params.put("timestamp", "timestamp");
//            params.put("signstr", "nonoono");
//
//            noticeService.verCode(params);
//        } catch (java.lang.Exception e) {
//            serverCall.setReason(e.getMessage());
//            ret = false;
//        }
//
//        return ret;
//    }
//}
