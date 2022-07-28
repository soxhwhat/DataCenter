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
 * 队列状态上报处理器
 *
 * @author zhiwei.zhai
 * @date 2022-04-27
 */
@Slf4j
@Component
public class QueueStatusKafkaHandler extends AbstractKafkaHandler {

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(QUEUE_WAIT_BEAT);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.QueueStatusKafkaHandler;
    }

    @Override
    public String getTopic(EventContext ec) {
        return kafkaProperties.getTopic().getQueue();
    }

    /// TODO TODO TODO 临时修改
    @Override
    public Object getData(EventContext ec) {
        return ec.getEvent();
    }
}
