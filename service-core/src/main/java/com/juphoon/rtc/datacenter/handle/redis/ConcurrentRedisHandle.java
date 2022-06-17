package com.juphoon.rtc.datacenter.handle.redis;

import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/5/5 14:50
 * @Description:
 */
@Slf4j
@Component
public class ConcurrentRedisHandle extends AbstractRedisHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.CONCURRENT_BEAT);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.ConcurrentRedisHandler;
    }

    @Override
    public String keyName() {
        return "jrtc:monitor:concurrent:status";
    }

    @Override
    public RedisTemplate redisTemplate() {
        return redisTemplate;
    }

    /**
     * 排队机上报间隔大约3-4秒
     * @return
     */
    @Override
    public Duration expireTime() {
        return properties.getRedisEvent().getStaffExpireTime();
    }

    @Override
    public boolean handle(EventContext ec) {
        try {
            log.info("ec:{},keyName:{}", ec.body(), keyName());
            String key = ec.getEvent().getUuid() + ":" + ec.getEvent().getDomainId() + ":" + ec.getEvent().getAppId();
            redisTemplate().boundHashOps(keyName()).put(key, IronJsonUtils.objectToJson(ec.getEvent()));
            if (expireTime().toMillis() != 0) {
                redisTemplate().boundHashOps(keyName()).expire(expireTime());
            }
        } catch (DataAccessException e) {
            log.error("DataAccessException:{}", e);
            return false;
        } catch (Exception e) {
            log.error("{}", e);
            return true;
        }
        log.info("执行{}结束", this.getClass().getName());
        return true;
    }
}
