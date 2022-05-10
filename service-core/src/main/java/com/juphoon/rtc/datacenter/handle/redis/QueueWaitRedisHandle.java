package com.juphoon.rtc.datacenter.handle.redis;

import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/5/5 14:50
 * @Description:
 */
@Slf4j
@Component
public class QueueWaitRedisHandle extends AbstractBatchRedisHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.QUEUE_WAIT_BEAT);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.QueueWaitRedisHandler;
    }

    @Override
    public String keyName() {
        return "jrtc:monitor:queue:wait:status";
    }

    @Override
    public RedisTemplate redisTemplate() {
        return redisTemplate;
    }

    @Override
    public Duration expireTime() {
        return properties.getRedisEvent().getQueueExpireTime();
    }
}