package com.juphoon.rtc.datacenter.servicecore.handle.http.agree;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


/**
 * <p>登录请求</p>
 *
 * @author ajian.zheng
 * @date 2022-03-22
 */
@Slf4j
@Component
public class AgreeUserLoginRequestHandler extends AbstractAgreeNoticeHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AgreeUserLoginRequestHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(EventType.USER_LOGIN_REQUEST);
    }

    @Override
    public String endpoint() {
        return "/userlogin_request";
    }
}
