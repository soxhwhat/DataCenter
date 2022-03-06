package cloud.juphoon.jrtc.handler;

import cloud.juphoon.jrtc.api.Event;
import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.EventType;
import cloud.juphoon.jrtc.api.ICare;
import cloud.juphoon.jrtc.processor.AbstractEventProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
public abstract class AbstractEventHandler implements IEventHandler, ICare {

    public AbstractEventProcessor processor;

    @Override
    public boolean care(Event event) {
        List<EventType> eventTypes = careEvents();
        if (eventTypes != null) {
            return eventTypes.contains(event.getEventType());
        } else {
            return false;
        }
    }

    @Override
    public boolean isRedo() {
        return false;
    }
}
