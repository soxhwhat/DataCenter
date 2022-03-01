package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.api.IConfig;
import lombok.Data;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    2/18/22 11:13 AM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
public class EventQueueConfig implements IConfig {
    /**
     * 内存队列大小
     */
    private int queueSize = 1024;

    /**
     * 数据库名
     */
    private String dbName;
}
