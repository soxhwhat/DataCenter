package cloud.juphoon.jrtc.handle.kafka;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.handler.AbstractEventHandler;
import cloud.juphoon.jrtc.processor.KafkaProcessor;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:51
 * @Description:
 */
public abstract class AbstractKafkaHandler extends AbstractEventHandler {

    public DataListenableFutureCallback callback = new DataListenableFutureCallback();

    public KafkaProcessor kafkaProcessor;

    public KafkaProcessor getKafkaProcessor() {
        if (kafkaProcessor == null) {
            kafkaProcessor = (KafkaProcessor) processor;
        }
        return kafkaProcessor;
    }

    public KafkaTemplate getKafkaTemplate() {
        return getKafkaProcessor().getKafkaTemplate();
    }

    public String getTopic(EventContext ec) {
        return getKafkaProcessor().getConfig().collectionMap.get(ec.getEvent().getType().toString());
    }

}
