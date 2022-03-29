package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.TICKER_COMPLETE;

/**
 * <p>客服话单事件处理handler</p>
 *
 * @author ajian.zheng
 * @date 2022-03-29
 */
@Slf4j
@Component
public class AcdTicketEventMongoHandler extends AbstractMongoHandler {
    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdTicketEventMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(TICKER_COMPLETE);
    }

    @Override
    public String collectionName(EventContext ec) {
        return "jrtc.records";
    }
}
