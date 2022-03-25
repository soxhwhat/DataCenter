package com.juphoon.rtc.datacenter.cube.notice;

import Common.Exception;
import Common.ServerCall;
import com.juphoon.rtc.datacenter.entity.notice.CubeNoticeResponse;
import com.juphoon.rtc.datacenter.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rongbin.huang
 * @create 2019-12-16 4:38 PM
 **/
@Slf4j
@Component
public class AuthServerServerImpl extends Event.AuthServerServer {

    @Autowired
    private NoticeService noticeService;

    @Override
    public boolean authUser(ServerCall __call, String account, String random, String md5pwd) throws Exception {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("userid", account);
            params.put("username", "no");
            params.put("timestamp", "timestamp");
            params.put("signstr", "nonoono");
            CubeNoticeResponse result = noticeService.verCode(params);

            authUser_end(__call, result.isRet());

        } catch (java.lang.Exception e) {
            __call.setReason(e.getMessage());
            authUser_end(__call, false);
        }
        return true;
    }
}
