package cloud.juphoon.jrtc.handle.kafka;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.EventType;
import cloud.juphoon.jrtc.handler.AbstractEventHandler;
import cloud.juphoon.jrtc.process.KafkaProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/4 9:45
 * @Description:
 */
@Slf4j
public class EventKafkaHandler extends AbstractEventHandler {
    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.TICKET_EVENT);
    }

    DataListenableFutureCallback callback = new DataListenableFutureCallback();

    @Override
    public boolean handle(EventContext ec) {
        KafkaProcessor processor = (KafkaProcessor)this.processor;
        KafkaTemplate kafkaTemplate = processor.getKafkaTemplate();
        KafkaProcessor.Config config = processor.getConfig();
        String topic = config.collectionMap.get(ec.getEvent().getNumber());
        try {
            kafkaTemplate.send(topic, ec.getEvent().toString()).addCallback(callback);
        } catch (KafkaException kafkaException){
            return false;
        }
        log.info("执行kafkaHandle结束");
        return true;
    }
}
