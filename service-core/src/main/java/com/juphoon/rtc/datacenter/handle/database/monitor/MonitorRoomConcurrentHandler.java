package com.juphoon.rtc.datacenter.handle.database.monitor;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorRoomConcurrentPO;
import com.juphoon.rtc.datacenter.handler.AbstractHandler;
import com.juphoon.rtc.datacenter.mapper.MonitorRoomConcurrentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * <p>视频客服排队机 队列监控</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 6/21/22 17:17
 * @description
 *
 */
@Slf4j
@Component
public class MonitorRoomConcurrentHandler extends AbstractHandler<StateContext> {
    @Autowired
    private MonitorRoomConcurrentMapper roomConcurrentMapper;

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(EventType.ROOM_BEAT);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.MonitorRoomConcurrentHandler;
    }

    @Override
    public boolean handle(StateContext context) throws Exception {
        log.debug("context:{}", context);

        MonitorRoomConcurrentPO po = MonitorRoomConcurrentPO.fromState(context);

        log.debug("po:{}", po);

        // update or insert
        int ret = roomConcurrentMapper.updateByFromAndDomainIdAndAppId(po);
        if (0 == ret) {
            roomConcurrentMapper.insert(po);
        }

        return true;
    }
}
