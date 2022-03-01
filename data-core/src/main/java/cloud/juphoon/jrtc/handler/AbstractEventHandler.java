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

    @Override
    public boolean isRedo() {
        return false;
    }
}
