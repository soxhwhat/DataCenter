package com.juphoon.rtc.datacenter.handle.http.agree;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
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
    public List<EventType> careEvents() {
        return Collections.singletonList(EventType.LOGOUT_EVENT);
    }

    @Override
    public String endpoint() {
        return "/userlogout_notify";
    }

    @Override
    public Map<String, Object> handleRequest(EventContext ec) {
        // todo
        return null;
    }
}
