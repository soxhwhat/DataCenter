package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.api.EventContext;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
public interface IEventQueueService {

    /**
     * 提交事件
     *         // TODO
     *         // 1. 数据落地
     *         // 2. 有界内存队列
     *         // 3. 驱动线程池消费队列
     * @param ec
     * @return
     * @throws Exception
     */
    public void submit(EventContext ec) throws Exception;

    /// TODO
    /// 线程池消费事件   回调到 process 开启消费流程
    /// processor.handle(ec);

}