package com.juphoon.rtc.datacenter.binlog.impl;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.binlog.mapper.EventLogMapper;
import com.juphoon.rtc.datacenter.handler.IHandler;
import com.juphoon.rtc.datacenter.utils.JrtcIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/31/22 5:43 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public abstract class AbstractEventLogService implements ILogService<EventContext> {
    /**
     * 获取 binlog mapper
     * @return
     */
    public abstract EventLogMapper getEventLogMapper();

    @Override
    public void save(EventContext context) {
        assert null != context : "参数为空";

        log.debug("context:{}", context);

        EventBinLogPO po = EventBinLogPO.fromEventContext(context);

        getEventLogMapper().save(po);
    }

    @Override
    public void saveRedo(EventContext context, IHandler handler) {
        assert null != context : "context 参数为空";
        assert null != handler : "handler 参数为空";

        log.debug("context:{}", context);

        context.setRedoHandler(handler.getId());

        EventBinLogPO po = EventBinLogPO.fromEventContext(context);

        /// redo 更新 id，作为一个新的事件
        po.setId(JrtcIdGenerator.newId());

        getEventLogMapper().save(po);
    }

    @Override
    public void save(List<EventContext> list) {
        assert !CollectionUtils.isEmpty(list) : "参数为空";

        log.debug("context:{}", list.size());

        List<EventBinLogPO> pos = list.stream().map(EventBinLogPO::fromEventContext).collect(Collectors.toList());

        getEventLogMapper().saveList(pos);
    }

    @Override
    public void remove(EventContext context) {
        assert null != context : "参数为空";

        log.debug("context:{}", context);

        getEventLogMapper().remove(context.getId());
    }

    @Override
    public List<EventContext> find(int size) {
        List<EventBinLogPO> list = getEventLogMapper().find(size);
        if (CollectionUtils.isEmpty(list)) {
            return new LinkedList<>();
        }

        return list.stream().map(EventBinLogPO::toEventContext).collect(Collectors.toList());
    }

    @Override
    public void updateRetryCount(EventContext context) {
        getEventLogMapper().updateRetryCount(EventBinLogPO.fromEventContext(context));
    }

    @Override
    public void start() {
        getEventLogMapper().createTable();
    }
}
