package com.juphoon.rtc.datacenter.processor.queue;

import com.juphoon.rtc.datacenter.api.IConfig;
import lombok.Getter;
import lombok.Setter;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.*;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 11:13 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Getter
@Setter
public class QueueServiceConfig implements IConfig {
    /**
     * 队列类型
     * disruptor/none
     */
    private String type = QUEUE_SERVICE_CONFIG_TYPE_EXECUTOR;

    /**
     * 队列大小
     */
    private int queueSize = 4096;

    /**
     * 核心线程数:Runtime.getRuntime().availableProcessors()
     * IO密集型任务最佳线程数大于CPU核心数很多倍，故设置为CPU核心数的2n + 1
     */
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;

    /**
     * 最大线程数设置为核心核心线程数相同
     * 1.避免内存交换
     * 2.创建固定大小的线程池
     */
    private int maxPoolSize = corePoolSize;

    /**
     * 最大空闲时间
     */
    private long keepAliveTime = 60L;
}
