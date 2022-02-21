package cloud.juphoon.jrtc.mq;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    2/18/22 11:13 AM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */ 
public class EventQueueConfig {
    /**
     * 内存队列大小
     */
    private int queueSize = 1000;

    /**
     * 数据库文件名
     */
    private String dbFileName;
}
