package com.juphoon.rtc.datacenter.binlog.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.binlog.mapper.EventLogMapper;
import com.juphoon.rtc.datacenter.handler.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;

/**
 * <p>快速的</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/31/22 5:43 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component(EVENT_BIN_LOG_IMPL_FLASH)
public class FlashEventLogServiceImpl implements ILogService<EventContext> {
    @Autowired
    private EventLogMapper logMapper;

    @Override
    public void save(EventContext context) {
        assert null != context : "参数为空";

        log.debug("context:{}", context);

        EventBinLogPO po = EventBinLogPO.fromEventContext(context);

        logMapper.save(po);
    }

    @Override
    public void save(EventContext context, IHandler handler) {
        assert null != context : "context 参数为空";
        assert null != handler : "handler 参数为空";

        log.debug("context:{}", context);

        context.setRedoHandler(handler.getId());

        EventBinLogPO po = EventBinLogPO.fromEventContext(context);

        logMapper.save(po);
    }

    @Override
    public void save(List<EventContext> list) {
        assert !CollectionUtils.isEmpty(list) : "参数为空";

        log.debug("context:{}", list.size());

        List<EventBinLogPO> pos = list.stream().map(EventBinLogPO::fromEventContext).collect(Collectors.toList());

        logMapper.saveList(pos);
    }

    @Override
    public void remove(EventContext context) {
        assert null != context : "参数为空";

        log.debug("context:{}", context);

        logMapper.remove(context.getId());
    }


}
