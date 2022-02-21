package cloud.juphoon.jrtc.handler;

import cloud.juphoon.jrtc.api.Event;
import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.EventType;
import cloud.juphoon.jrtc.api.ICare;

import java.util.List;

/**
 * @Author: 郑建
 * @Date: 2022-02-18
 * @Description:
 */
public abstract class AbstractCareAllEventHandler extends AbstractEventHandler {

    @Override
    public List<EventType> careEvents() {
        return null;
    }

    @Override
    public boolean care(Event event) {
        return true;
    }
}
