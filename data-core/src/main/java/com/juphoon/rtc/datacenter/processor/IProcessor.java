package com.juphoon.rtc.datacenter.processor;


import com.juphoon.rtc.datacenter.api.INamed;
import com.juphoon.rtc.datacenter.api.IService;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.handler.IHandler;
import com.juphoon.rtc.datacenter.processor.queue.IQueueService;
import com.juphoon.rtc.datacenter.processor.queue.QueueServiceConfig;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 9:44
 * @update:
 * <p>1. 2022-03-24. ajian.zheng 增加可命名接口</p>
 */
public interface IProcessor<T> extends INamed, IService {
    /**
     * 设置处理器名
     * @return 处理器id
     */
    ProcessorId processorId();

    /**
     * 设置事件存储
     *
     * @return
     */
    ILogService<T> logService();

    /**
     * 构造事件消费队列服务
     *
     * @return
     */
    IQueueService<T> queueService();

    /**
     * 构造队列实例
     *
     * @param config
     * @return
     */
    void buildQueueService(QueueServiceConfig config);

    /**
     * 处理事件
     *
     * @param t
     * @throws Exception
     */
    void commit(T t);

    /**
     * 处理事件
     *
     * @param t
     * @throws Exception
     */
    void submit(T t);

    /**
     * 处理事件
     *
     * @param t
     * @throws Exception
     */
    void process(T t);

    /**
     * 组装 handler
     * @param handler
     */
    void addHandler(IHandler<T> handler);

    /**
     * 获取handler 名
     * @return
     */
    @Override
    default String getName() {
        return processorId().getName();
    }

    /**
     * 获取handler Id
     * @return
     */
    @Override
    default String getId() {
        return processorId().getId();
    }
}
