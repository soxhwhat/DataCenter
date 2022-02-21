package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.IConfig;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
public interface IEventQueue {

    /**
     * 初始化线程池
     */
    int init(IConfig config);

    /**
     * 出队
     * @return
     */
    EventContext pop();

    /**
     * 入队
     * @param ec
     * @return
     */
    boolean push(EventContext ec) throws Exception;

    /**
     * 获取队列大小
     * @return
     */
    int getSize();
}
