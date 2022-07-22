package com.juphoon.rtc.datacenter.datacore.binlog.impl;

import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.binlog.entity.StateBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FlashStateLogMapper;
import com.juphoon.rtc.datacenter.datacore.handler.IHandler;
import com.juphoon.rtc.datacenter.datacore.utils.JrtcIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.STATE_BIN_LOG_IMPL_FLASH;

/**
 * <p>FlashStateLogServiceImpl 实现类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/31/22 5:43 PM
 */
@Slf4j
@Component(STATE_BIN_LOG_IMPL_FLASH)
public class FlashStateLogServiceImpl implements ILogService<StateContext> {
    @Autowired
    private FlashStateLogMapper logMapper;

    @Override
    public void save(StateContext context) {
        assert null != context : "参数为空";

        log.debug("context:{}", context);

        StateBinLogPO po = StateBinLogPO.fromContext(context);

        logMapper.save(po);
    }

    @Override
    public void saveRedo(StateContext context, IHandler handler) {
        assert null != context : "context 参数为空";
        assert null != handler : "handler 参数为空";

        log.debug("context:{}", context);

        context.setRedoHandler(handler.getId());

        StateBinLogPO po = StateBinLogPO.fromContext(context);

        /// redo 更新 id，作为一个新的事件
        po.setId(JrtcIdGenerator.newId());

        logMapper.save(po);
    }

    @Override
    public void save(List<StateContext> list) {
        assert !CollectionUtils.isEmpty(list) : "参数为空";

        log.debug("context:{}", list.size());

        List<StateBinLogPO> pos = list.stream().map(StateBinLogPO::fromContext).collect(Collectors.toList());

        logMapper.saveList(pos);
    }

    @Override
    public void remove(StateContext context) {
        assert null != context : "参数为空";

        log.debug("context:{}", context);

        logMapper.remove(context.getId());
    }

    @Override
    public List<StateContext> find(int size) {
        List<StateBinLogPO> list = logMapper.find(size);
        if (CollectionUtils.isEmpty(list)) {
            return new LinkedList<>();
        }

        return list.stream().map(StateBinLogPO::toContext).collect(Collectors.toList());
    }

    @Override
    public void updateRetryCount(StateContext context) {
        logMapper.updateRetryCount(StateBinLogPO.fromContext(context));
    }

    @Override
    public void start() {
        logMapper.createTable();
    }
}
