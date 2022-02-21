package cloud.juphoon.jrtc.api;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Getter
@Setter
public class EventContext {
    /**
     * 消息体
     */
    private Event event;

    /**
     * 重做handle的Class列表
     */
    private List<String> redoClzList;

    /**
     * 创建时间
     */
    private long createdTimestamp = System.currentTimeMillis();

    /**
     * 首次消费时间
     */
    private long beginTimestamp = 0;

    /**
     * 重做次数
     */
    private int retryCount = -1;

    /**
     * 处理
     */
    public void handle() {
        retryCount++;
        beginTimestamp = System.currentTimeMillis();
    }

    public void redo(String clz) {
        if (null == redoClzList) {
            redoClzList = new LinkedList<>();
        }
        redoClzList.add(clz);
    }
}
