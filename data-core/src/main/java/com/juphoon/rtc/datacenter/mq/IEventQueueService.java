package com.juphoon.rtc.datacenter.mq;

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
     * @param ec
     * @return
     * @throws Exception
     */
    void submit(EventContext ec);

    /// TODO
    /// 线程池消费事件   回调到 process 开启消费流程
    /// processor.handle(ec);

    void redo(EventContext ec,String handleName);

    /**
     * 1、判断当前事件是否还有需要重做的handle
     * 2、如没有 则删除event事件
     * 3、如有 则不处理 等待下次扫描重做
     * @param ec
     */
    void processOk(EventContext ec);

    void redoOk(String redoId);

}