package cloud.juphoon.jrtc.processor;


import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.handler.IEventHandler;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 9:44
 * @Description:
 */
public interface IEventProcessor {


    /**
     * 处理事件
     *
     * @param ec
     * @throws Exception
     */
    void process(EventContext ec) ;
}
