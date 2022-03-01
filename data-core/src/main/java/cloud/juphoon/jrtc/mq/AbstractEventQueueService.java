package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.mapper.ILogMapper;
import cloud.juphoon.jrtc.processor.AbstractEventProcessor;
import cloud.juphoon.jrtc.processor.IEventProcessor;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 19:23
 * @Description:
 */
public abstract class AbstractEventQueueService implements IEventQueueService {

    protected AbstractEventProcessor processor;

    /**
     * 设置处理器
     *
     * @param processor
     */
    public void setProcessor(AbstractEventProcessor processor) {
        this.processor = processor;
    }
    /**
     * 获取处理器
     */
    public AbstractEventProcessor getProcessor() {
        return processor;
    }

    /// TODO
    /// 线程池消费事件   回调到 process 开启消费流程
    /// processor.handle(ec);

}