package com.juphoon.rtc.datacenter.handle.redis;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StaffRedisHandle extends AbstractRedisHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.STAFF_BEAT);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.StaffRedisHandler;
    }

    @Override
    public String keyName() {
        return "jrtc.staff.status";
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
        return Duration.ofSeconds(15);
    }
}
