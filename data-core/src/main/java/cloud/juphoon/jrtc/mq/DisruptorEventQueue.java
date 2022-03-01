package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.IConfig;
import cloud.juphoon.jrtc.handler.IEventHandler;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/24 10:16
 * @Description:
 */
@Slf4j
public class DisruptorEventQueue implements IEventQueue{

    Disruptor<EventContext> disruptor;

    @Override
    public void init(EventQueueConfig config,AbstractEventQueueService queueService) {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int counter = 0;
            private String prefix = "disruptor-pool-";

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, prefix + "-" + counter++);
            }
        };


        disruptor = new Disruptor(EventContext::new, config.getQueueSize(), threadFactory, ProducerType.MULTI,
                new BlockingWaitStrategy());

        WorkHandler[] workHandlers = new WorkHandler[10];
        for (int i = 0 ; i < 10 ; i ++){
            workHandlers[i] = new DisruptorEventHandle(queueService.getProcessor(), queueService);
        }
        disruptor.handleEventsWithWorkerPool(workHandlers);
        disruptor.start();
    }

    @Override
    public EventContext pop() {
        return null;
    }

    @Override
    public boolean push(EventContext ec) throws Exception {
        try {
            disruptor.publishEvent((eventContext, l) -> {
                eventContext.setEvent(ec.getEvent());
                eventContext.setBeginTimestamp(ec.getBeginTimestamp());
                eventContext.setCreatedTimestamp(ec.getBeginTimestamp());
                eventContext.setRetryCount(ec.getRetryCount());
                eventContext.setRedoClzList(ec.getRedoClzList());
            });
        } catch (Exception e){
            log.error("push内存队列失败:{}",e);
            throw e;
        }
        return true;
    }

    @Override
    public int getSize() {
        return Long.valueOf(disruptor.getRingBuffer().remainingCapacity()).intValue();
    }
}
