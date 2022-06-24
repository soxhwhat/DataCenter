package com.juphoon.rtc.datacenter.processor.loader;

import com.juphoon.rtc.datacenter.api.BaseContext;
import com.juphoon.rtc.datacenter.api.IService;
import com.juphoon.rtc.datacenter.binlog.ILogService;

import java.util.List;

/**
 * <p>binlog数据加载器</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 6/24/22 8:32 PM
 */
public interface IContextLoader<T extends BaseContext> extends IService {
    /**
     * 加载数据
     *
     * @param logService
     * @return
     */
    List<T> loadContexts(ILogService<T> logService);
}
