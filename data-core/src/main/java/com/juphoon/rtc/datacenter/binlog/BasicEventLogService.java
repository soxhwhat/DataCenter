package com.juphoon.rtc.datacenter.binlog;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.handler.IHandler;

/**
 * <p>EventLog抽象基类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/20/22 7:17 PM
 */
public abstract class BasicEventLogService implements ILogService<EventContext> {
    @Override
    public void save(EventContext eventContext) {
// todo
    }

    @Override
    public void remove(EventContext eventContext) {
// todo
    }

    @Override
    public void saveRedo(EventContext eventContext, IHandler<EventContext> handler) {
// todo
    }

    @Override
    public void removeRedo(EventContext eventContext) {
// todo
    }
}
