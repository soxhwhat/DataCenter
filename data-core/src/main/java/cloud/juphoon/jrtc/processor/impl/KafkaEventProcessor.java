package cloud.juphoon.jrtc.processor.impl;

import cloud.juphoon.jrtc.api.EventType;
import cloud.juphoon.jrtc.processor.AbstractEventProcessor;

import java.util.List;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    2/18/22 10:39 AM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */ 
public class KafkaEventProcessor extends AbstractEventProcessor {
    @Override
    public List<EventType> careEvents() {
        return null;
    }

    public KafkaEventProcessor() {
    }

    public KafkaEventProcessor(Config config) {
    }

    public static class Config {

    }
}
