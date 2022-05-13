package com.juphoon.rtc.datacenter.handle.database;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/5/10 9:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public abstract class AbstractDatabaseHandler<T> extends AbstractEventHandler {

    @Override
    public boolean handle(EventContext ec) {
        boolean flag = false;
        try {
            T t = preData(ec.getEvent());
            insertSelective(t);
            flag = true;
        } catch (Exception e) {
            log.warn("ec.id[{}], handler[{}] handle failed!", ec.getId(), handlerId().getName());
            log.warn(e.getMessage(), e);
            return flag;
        }
        return flag;
    }

    /**
     * 获取PO对象
     * @param  event
     * @return T
     */
    public abstract T preData(Event event);

    /**
     * 插入数据
     * @param  t
     * @return i
     */
    public abstract Integer insertSelective(T t);

}
