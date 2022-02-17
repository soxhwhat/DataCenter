package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.accepter.EventContent;

import java.util.List;

/**
 *
 * @author zhiwei.zhai
 */
public interface EventCacheService {

    /**
     * 插入事件日志
     * @param eventContent
     */
    void insertEvent(EventContent eventContent);

    /**
     * 删除事件日志
     * @param eventContent
     */
    void removeEvent(EventContent eventContent);
    /**
     * 插入句柄日志
     * @param eventContent
     */
    void insertHandle(EventContent eventContent);

    /**
     * 删除句柄日志
     * @param eventContent
     */
    void removeHandle(EventContent eventContent);

    /**
     * 获取失败句柄
     * @return
     */
    List<EventContent> getFailHandleLog();
}
