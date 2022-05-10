package com.juphoon.rtc.datacenter.handle.redis;

import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;


/**
 * <p>mongodb操作handler抽象类</p>
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:37
 * @update
 */
@Slf4j
public abstract class AbstractRemoveRedisHandler extends AbstractRedisHandler {

    @Override
    public boolean handle(EventContext ec) {
        log.info("AbstractRemoveRedisHandler,ec:{},keyName:{}", ec.body(), keyName());
        try {
            redisTemplate().boundHashOps(keyName()).delete(ec.getEvent().agentId());
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
