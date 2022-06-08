package com.juphoon.rtc.datacenter.binlog.impl;

import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.binlog.entity.LogBinLogPO;
import com.juphoon.rtc.datacenter.binlog.entity.StateBinLogPO;
import com.juphoon.rtc.datacenter.binlog.mapper.flash.FlashLogLogMapper;
import com.juphoon.rtc.datacenter.binlog.mapper.flash.FlashStateLogMapper;
import com.juphoon.rtc.datacenter.handler.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.STATE_BIN_LOG_IMPL_FLASH;

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
    public void save(StateContext context, IHandler handler) {
        assert null != context : "context 参数为空";
        assert null != handler : "handler 参数为空";

        log.debug("context:{}", context);

        context.setRedoHandler(handler.getId());

        StateBinLogPO po = StateBinLogPO.fromContext(context);

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
    public void start() {
        logMapper.createTable();
    }
}
