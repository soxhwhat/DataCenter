package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.handler.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.LOG_EVENT;

/**
 * <p>mongodb操作handler抽象类</p>
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:37
 * @update
 * <li>1. 2022-03-29. ajian.zheng 抽象公共逻辑，将集合名放到上层，公共handle逻辑放到抽象层collectionName</li>
 * <li>2. 2022-03-31. ajian.zheng 支持多数据源，将数据源放到上层，公共handle逻辑放到抽象层mongoTemplate</li>
 */
@Slf4j
@Component
public class LogHandler extends AbstractHandler<LogContext> {

    @Override
    public HandlerId handlerId() {
        return HandlerId.LogMongoHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(
                LOG_EVENT
        );
    }

    @Override
    public boolean handle(LogContext ec) {
        return true;
    }
}
