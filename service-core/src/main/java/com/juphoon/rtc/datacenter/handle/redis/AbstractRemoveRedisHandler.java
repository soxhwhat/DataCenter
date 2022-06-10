package com.juphoon.rtc.datacenter.handle.redis;

import com.juphoon.rtc.datacenter.api.StateContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;

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
    public boolean handle(StateContext ec) {
        log.info("AbstractRemoveRedisHandler,ec:{},keyName:{}", ec.getId(), keyName());
        try {
            /// TODO
            /// TODO
            /// TODO
            /// TODO
//            redisTemplate().boundHashOps(keyName()).delete(ec.getState().agentId());
            redisTemplate().boundHashOps(keyName()).delete(ec.getState().getUniqueId());
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
