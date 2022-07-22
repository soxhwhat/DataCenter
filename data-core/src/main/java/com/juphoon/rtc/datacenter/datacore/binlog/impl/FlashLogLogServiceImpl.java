package com.juphoon.rtc.datacenter.datacore.binlog.impl;

import com.juphoon.rtc.datacenter.datacore.api.LogContext;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.binlog.entity.LogBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FlashLogLogMapper;
import com.juphoon.rtc.datacenter.datacore.handler.IHandler;
import com.juphoon.rtc.datacenter.datacore.utils.JrtcIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOG_BIN_LOG_IMPL_FLASH;

/**
 * <p>FlashLogLogServiceImpl 实现类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/31/22 5:43 PM
 */
@Slf4j
@Component(LOG_BIN_LOG_IMPL_FLASH)
public class FlashLogLogServiceImpl implements ILogService<LogContext> {
    @Autowired
    private FlashLogLogMapper logMapper;

    @Override
    public void save(LogContext context) {
        assert null != context : "参数为空";

        log.debug("context:{}", context);

        LogBinLogPO po = LogBinLogPO.fromContext(context);

        logMapper.save(po);
    }

    @Override
    public void saveRedo(LogContext context, IHandler handler) {
        assert null != context : "context 参数为空";
        assert null != handler : "handler 参数为空";

        log.debug("context:{}", context);

        context.setRedoHandler(handler.getId());

        LogBinLogPO po = LogBinLogPO.fromContext(context);

        /// redo 更新 id，作为一个新的事件
        po.setId(JrtcIdGenerator.newId());

        logMapper.save(po);
    }

    @Override
    public void save(List<LogContext> list) {
        assert !CollectionUtils.isEmpty(list) : "参数为空";

        log.debug("context:{}", list.size());

        List<LogBinLogPO> pos = list.stream().map(LogBinLogPO::fromContext).collect(Collectors.toList());

        logMapper.saveList(pos);
    }

    @Override
    public void remove(LogContext context) {
        assert null != context : "参数为空";

        log.debug("context:{}", context);

        logMapper.remove(context.getId());
    }

    @Override
    public List<LogContext> find(int size) {
        List<LogBinLogPO> list = logMapper.find(size);
        if (CollectionUtils.isEmpty(list)) {
            return new LinkedList<>();
        }

        return list.stream().map(LogBinLogPO::toContext).collect(Collectors.toList());
    }

    @Override
    public void updateRetryCount(LogContext context) {
        logMapper.updateRetryCount(LogBinLogPO.fromContext(context));
    }

    @Override
    public void start() {
        logMapper.createTable();
    }
}
