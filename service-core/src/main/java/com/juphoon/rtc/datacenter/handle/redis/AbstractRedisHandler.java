package com.juphoon.rtc.datacenter.handle.redis;

import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;


/**
 * <p>redis操作handler抽象类</p>
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:37
 * @update
 */
@Slf4j
public abstract class AbstractRedisHandler extends AbstractEventHandler {

    @Override
    public boolean handle(EventContext ec) {
        try {
            log.info("ec:{},keyName:{}", ec.body(), keyName());
            redisTemplate().boundHashOps(keyName()).put(ec.getEvent().getUuid(), IronJsonUtils.objectToJson(ec.getEvent()));
            redisTemplate().boundHashOps(keyName()).expire(expireTime());
        } catch (DataAccessException e) {
            log.error("DataAccessException:{}", e);
            return false;
        } catch (Exception e) {
            log.error("{}", e);
            return true;
        }
        log.info("执行RedisHandle结束");
        return true;
    }

    /**
     * 集合名
     *
     * @return
     */
    public abstract String keyName();

    /**
     * 获取redis模板
     *
     * @return
     */
    public abstract RedisTemplate redisTemplate();

    /**
     * 获取redis模板
     *
     * @return
     */
    public abstract Duration expireTime();
}
