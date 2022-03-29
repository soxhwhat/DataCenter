package com.juphoon.rtc.datacenter.service;

import java.util.List;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ke.wang@juphoon.com
 * @date    2022/3/24 15:12
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@SuppressWarnings("PMD")
public interface CacheService {
    void hPut(String hash, String key, Object obj);

    Object hGet(String hash, String key);

    List<Object> hGetList(String hash);

    void hRemove(String hash, String uri);
}
