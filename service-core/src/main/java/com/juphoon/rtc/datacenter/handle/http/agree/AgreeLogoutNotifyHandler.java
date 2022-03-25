package com.juphoon.rtc.datacenter.handle.http.agree;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * <p>登出通知</p>
 *
 * @author ajian.zheng
 * @date 2022-03-22
 */
@Slf4j
@Component
public class AgreeLogoutNotifyHandler extends AbstractAgreeNoticeHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AgreeUserLogoutNotifyHandler;
    }

    @Override
    public String endpoint() {
        return "/userlogout_notify";
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(EventType.LOGOUT_EVENT);
    }

    @Override
    public Map<String, String> handleRequest(EventContext ec) {
        //noticeServiceImpl类中已经封装好了参数，这一版暂时先这样，后续定义新的rpc接口时再重新封装
        return ec.getEvent().getParams();
//        Map<String, String> params = ec.convertStringMap();
//        deleteRoomIdSuffix(params);
//        String uri = params.get("account");
//        String username = params.get("username");
//        if (StringUtils.isEmpty(uri)) {
//            uri = username;
//        }
//        uri = getUsername(uri);
//        params.put("userId", uri);
//        params.put("username", uri);
//        cacheService.hRemove(KEY_AGREE, uri);
//        params.put("errorcode", "0");
//        return ec.convertObjectMap(params);
    }
}
