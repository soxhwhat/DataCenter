package com.juphoon.rtc.datacenter.datacore.processor.loader;

import com.juphoon.rtc.datacenter.datacore.api.BaseContext;
import com.juphoon.rtc.datacenter.datacore.api.IService;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;

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
