package com.juphoon.rtc.datacenter.datacore.processor.queue;

import com.juphoon.rtc.datacenter.datacore.api.IService;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
public interface IQueueService<T> extends IService {
    /**
     * 提交事件
     *         // TODO
     *         // 1. 数据落地
     *         // 2. 有界内存队列
     *         // 3. 驱动线程池消费队列
     * @param t 事件
     * @return
     * @throws Exception
     */
    void submit(T t) throws Exception;

    /**
     * 事件处理成功
     *
     * @param t 事件
     */
    void success(T t);

    /**
     * 重做失败，清理标记，为了接口可读性
     *
     * @param t
     */
    default void redoFail(T t) {
        success(t);
    }

    /**
     * 初始化
     * @param config
     */
    default void init(QueueServiceConfig config){
    }


    // todo 增加延迟消费的submit
    // 重做事件最好加下延迟
}