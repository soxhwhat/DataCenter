package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.processor.IEventProcessor;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
public class EventQueueService extends AbstractEventQueueService {

    /**
     * with default config
     */
    public EventQueueService() {
        // todo
    }

    /**
     * spec config
     *
     * @param config
     */
    public EventQueueService(EventQueueConfig config) {
        // todo
    }

    /**
     * 提交事件
     *
     * @param ec
     * @return
     * @throws Exception
     */
    @Override
    public void submit(EventContext ec) throws Exception {
        // TODO
        // 1. 数据落地
        // 2. 有界内存队列
        // 3. 驱动线程池消费队列

    }

    /// TODO
    /// 线程池消费事件   回调到 process 开启消费流程
    /// processor.handle(ec);

}