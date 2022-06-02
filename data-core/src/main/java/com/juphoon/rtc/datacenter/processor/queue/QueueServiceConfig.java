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
    private String type = QUEUE_SERVICE_CONFIG_TYPE_DISRUPTOR;

    /**
     * 队列大小
     */
    private int queueSize = 1024;

    /**
     * blocking、sleeping、yielding
     */
    private String waitStrategy = QUEUE_SERVICE_CONFIG_WAIT_STRATEGY_BLOCKING;

    /**
     * single/multi
     */
    private String producerType = QUEUE_SERVICE_CONFIG_PRODUCER_TYPE_SINGLE;
}
