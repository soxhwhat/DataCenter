package cloud.juphoon.jrtc.handle.kafka;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 9:45
 * @Description:
 */
@Slf4j
@Configuration
public class EventKafkaHandler extends AbstractKafkaHandler {

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.TICKET_EVENT);
    }

    @Override
    public boolean handle(EventContext ec) {
        try {
            getKafkaTemplate().send(getTopic(ec), ec.getEvent().toString()).addCallback(callback);
        } catch (KafkaException kafkaException) {
            return false;
        }
        log.info("执行kafkaHandle结束");
        return true;
    }
}
