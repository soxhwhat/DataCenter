package com.juphoon.rtc.datacenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventPO;
import com.juphoon.rtc.datacenter.api.RedoPO;

import java.util.List;

/**
 * @date 20222-03-16
 * @author zhiwei.zhai
 */
public interface LogService {

    /**
     * 事件开始日志
     * @param ec
     */
    void eventBeginLog(EventContext ec, boolean pushSuccess);

    /**
     * 处理器失败日志
     * @param ec
     * @param handleClz
     */
    String handleFailLog(EventContext ec,String handleClz);

    /**
     * 重做成功
     * @param redoId
     */
    void redoSuccess(String redoId);

    /**
     * 全部重做
     * @param eventId
     */
    void allSuccess(String eventId);

    /**
     * 更新当前事件为就绪状态
     * @param eventId
     */
    void updateEventReady(String eventId);

    /**
     * 获取事件分页数据
     * @return
     */
    IPage<EventPO> getEventPageReadying();

    /**
     * 获取事件分页数据
     * @param eventId
     * @return
     */
    List<RedoPO> getRedoDataByEventId(String eventId);

    /**
     * 更新event事件
     * @param ec
     */
    void updateEventRetry(EventContext ec);

    /**
     * 初始化表
     */
    void initTables();
}
