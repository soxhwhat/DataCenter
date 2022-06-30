package com.juphoon.rtc.datacenter.servicecore.handle.http.agree;

import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/24 11:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
public class AgreeSendOnlineMessageHandler extends AbstractAgreeNoticeHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AgreeSendOnlineMessageHandler;
    }

    @Override
    public String endpoint() {
        return "/transbuffer_notify";
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(EventType.SEND_ONLINE);
    }
}
