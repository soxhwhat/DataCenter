package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.handler.AbstractHandle;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
public interface MemoryQueueManager {

    /**
     * 提交任务
     * @param obj
     */
    void submit(Object obj);

    /**
     * 初始化线程池
     */
    void init();

    /**
     * 装配句柄
     * @param handle
     */
    void setUp(List<AbstractHandle> handle);

    /**
     * 设置结束事件句柄
     * 结束事件需要保证最后一个执行 且其他事件必须已经结束
     */
    void setEndHandle();

    /**
     * 设置开始事件句柄
     * 开始事件需要保证第一个执行 且需要执行完毕后才执行其他事件
     */
    void setBeginHandle();

    /**
     * 启动线程池
     */
    void start();

}
