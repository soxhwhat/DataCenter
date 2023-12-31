package com.juphoon.rtc.datacenter.servicecore.handle.kafka;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.*;

/**
 * 话单状态上报处理器
 *
 * @author zhiwei.zhai
 * @date 2022-04-27
 */
@Slf4j
@Component
public class TicketKafkaHandler extends AbstractKafkaHandler {

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(TICKER_EVENT_WAIT, TICKER_EVENT_RING
                , TICKER_EVENT_TALK, TICKER_EVENT_OVERFLOW
                , TICKER_EVENT_TRANSFER, TICKER_EVENT_INVITE_AGENT
                , AGENT_OP_EVENT_BUSY, AGENT_OP_EVENT_FREE, AGENT_OP_EVENT_KEEP, AGENT_OP_EVENT_LOGIN
                , TICKER_COMPLETE);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.TicketKafkaHandler;
    }

    @Override
    public String getTopic(EventContext ec) {
        return kafkaProperties.getTopic().getTicket();
    }

    @Override
    public Object getData(EventContext ec) {
        return ec.getEvent();
    }
}
