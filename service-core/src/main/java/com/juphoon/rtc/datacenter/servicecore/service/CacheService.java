package com.juphoon.rtc.datacenter.servicecore.service;

import java.util.List;

/**
 * <p>赞同通知状态缓存抽象接口</p>
 * <p>TODO 终端传递准确状态</p>
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
