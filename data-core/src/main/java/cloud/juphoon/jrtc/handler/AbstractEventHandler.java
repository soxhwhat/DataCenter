package cloud.juphoon.jrtc.handler;

import cloud.juphoon.jrtc.api.Event;
import cloud.juphoon.jrtc.api.EventType;
import cloud.juphoon.jrtc.api.ICare;

import java.util.List;
import java.util.Set;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
public abstract class AbstractEventHandler implements IEventHandler, ICare {

    private Set<EventType> careEvents;

    @Override
    public boolean care(Event event) {
        assert careEvents != null : "关注事件列表必须为非空";

        return careEvents.contains(event.getEventType());
    }

    /**
     * 关注事件列表
     *
     * @return
     */
    public abstract List<EventType> careEvents();
}
