package cloud.juphoon.jrtc.handler;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.ICare;
import cloud.juphoon.jrtc.processor.AbstractEventProcessor;
import cloud.juphoon.jrtc.processor.IEventProcessor;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
public interface IEventHandler extends ICare {
//public interface IEventHandler extends ICare, Ordered {

    /**
     * 实际运行代码
     * @param ec
     */
    boolean handle(EventContext ec);

    boolean isRedo();
}
