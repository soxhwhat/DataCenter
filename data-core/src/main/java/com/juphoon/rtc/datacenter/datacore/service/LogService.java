package com.juphoon.rtc.datacenter.datacore.service;

import com.juphoon.rtc.datacenter.datacore.api.LogContext;
import com.juphoon.rtc.datacenter.datacore.processor.IProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 17:59
 * @Description:
 */
@Slf4j
public class LogService {
    private List<IProcessor<LogContext>> processors;

    public LogService(List<IProcessor<LogContext>> processors) {
        assert null != processors : "processors 不能为空";

        this.processors = processors;
    }

    /**
     * 提交任务
     *
     * @param lc
     * @throws Exception
     */
    public void commit(LogContext lc) {
        assert null != lc : "ec为空";

        for (IProcessor<LogContext> processor : processors) {
            log.debug("{} process ec:{}", processor.getName(), lc.getRequestId());

            processor.commit(lc);
        }
    }
}
