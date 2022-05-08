package com.juphoon.rtc.datacenter.handle.redis;

import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * <p>redis操作handler抽象类</p>
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:37
 * @update
 */
@Slf4j
public abstract class AbstractBatchRedisHandler extends AbstractEventHandler {

    @Override
    public boolean handle(EventContext ec) {

        try {
            log.info("ec:{},keyName:{}", ec.body(), keyName());
            Map<String, Event> data = ec.getEventList().stream().collect(Collectors.toMap(Event::getUuid, Function.identity()));
            BoundHashOperations ops = redisTemplate().boundHashOps(keyName());
            Set keys = ops.keys();
            if (!CollectionUtils.isEmpty(keys)){
                for (Object key : keys) {
                    ops.delete(key);
                }
            }
            data.entrySet().stream().forEach(entry ->
                    ops.put(entry.getKey(), IronJsonUtils.objectToJson(entry.getValue()))
            );
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
