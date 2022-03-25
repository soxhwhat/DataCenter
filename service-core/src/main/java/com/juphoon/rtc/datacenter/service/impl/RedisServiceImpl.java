package com.juphoon.rtc.datacenter.service.impl;

import com.juphoon.rtc.datacenter.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author rongbin.huang
 * @date 2021/3/29
 * @description
 */
@ConditionalOnProperty(prefix = "notice.config", name = "cache", havingValue = "redis", matchIfMissing = true)
@Slf4j
@Service
public class RedisServiceImpl implements CacheService {

    public RedisServiceImpl() {
        log.info("RedisServiceImpl");
    }

    private static final String NOTICE_KEY_SUFFIX = "juphoon:noticeEvent:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void hPut(String hash, String hKey, Object obj) {
        redisTemplate.opsForHash().put(NOTICE_KEY_SUFFIX + hash, hKey, obj);
        redisTemplate.expire(NOTICE_KEY_SUFFIX + hash, 2, TimeUnit.HOURS);
    }

    @Override
    public Object hGet(String hash, String hKey) {
        return redisTemplate.opsForHash().get(NOTICE_KEY_SUFFIX + hash, hKey);
    }

    @Override
    public List<Object> hGetList(String hash) {
        List<Object> values = redisTemplate.opsForHash().values(NOTICE_KEY_SUFFIX + hash);
        return values;
    }

    @Override
    public void hRemove(String hash, String uri) {
        redisTemplate.opsForHash().delete(NOTICE_KEY_SUFFIX + hash, uri);
    }
}
