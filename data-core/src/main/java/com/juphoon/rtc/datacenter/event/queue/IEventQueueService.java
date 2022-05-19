package com.juphoon.rtc.datacenter.event.queue;

import com.juphoon.rtc.datacenter.api.EventContext;

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
     * @param ec 事件
     * @return
     * @throws Exception
     */
    void submit(EventContext ec) throws Exception;

    /**
     * 事件处理成功
     *
     * @param ec 事件
     */
    void success(EventContext ec);

    // todo 增加延迟消费的submit
    // 重做事件最好加下延迟
}