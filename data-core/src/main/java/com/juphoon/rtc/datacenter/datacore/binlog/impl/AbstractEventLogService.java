package com.juphoon.rtc.datacenter.datacore.binlog.impl;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.EventLogMapper;
import com.juphoon.rtc.datacenter.datacore.handler.IHandler;
import com.juphoon.rtc.datacenter.datacore.utils.JrtcIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Event本地日志数据库服务基类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/31/22 5:43 PM
 */
@Slf4j
public abstract class AbstractEventLogService implements ILogService<EventContext> {
    /**
     * 获取 binlog mapper
     * @return
     */
    public abstract EventLogMapper getEventLogMapper();

    /**
     * sqlite数据库文件名
     *
     * @return
     */
    public abstract String dbFileName();

    @Override
    public void start() {
        try {
            // 建表
            getEventLogMapper().createTable();
            // 测试DB是否正常
            getEventLogMapper().find(1);
        } catch (MyBatisSystemException e) {
            // TODO 保留历史文件，重命名
            // 新建文件
            String fileName = System.getProperty("user.dir") + dbFileName();
            File file = new File(fileName);
            if (file.exists()) {
                /// 备份文件
                String bakFileName = fileName + ".bak." + FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date());
                file.renameTo(new File(bakFileName));

                /// 重建文件
                getEventLogMapper().createTable();
            }
        }
    }
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
    public synchronized void save(List<EventContext> list) {
        assert !CollectionUtils.isEmpty(list) : "参数为空";

        log.debug("context:{}", list.size());

        List<EventBinLogPO> pos = list.stream().map(EventBinLogPO::fromEventContext).collect(Collectors.toList());

        getEventLogMapper().saveList(pos);
    }

    @Override
    public synchronized void remove(EventContext context) {
        assert null != context : "参数为空";

        log.debug("context:{}", context);

        getEventLogMapper().remove(context.getId());
    }

    @Override
    public synchronized List<EventContext> find(int size) {
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
}
