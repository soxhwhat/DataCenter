package com.juphoon.rtc.datacenter.factory;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.exception.JrtcInvalidProcessorConfigurationException;
import com.juphoon.rtc.datacenter.handler.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>handler 工厂</p>
 *
 * 同 processor 工厂
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/16/22 8:54 AM
 */
@Slf4j
@Component
public class HandlerFactory {
    @Autowired
    private Map<String, IHandler<EventContext>> eventHandlers;

    @Autowired
    private Map<String, IHandler<LogContext>> logHandlers;

    @Autowired
    private Map<String, IHandler<StateContext>> stateHandlers;

    /**
     * 通过传入的processor名称获取对应的process实例
     * @param name
     * @return
     */
    public IHandler<EventContext> getEventHandler(String name) throws JrtcInvalidProcessorConfigurationException {
        IHandler<EventContext> handler = eventHandlers.get(name);

        if (null == handler) {
            log.warn("** 无效的 eventHandler 名:" + name + " **");
            throw new JrtcInvalidProcessorConfigurationException("** 无效的 handler 名:" + name + " **");
        }

        return handler;
    }

    public IHandler<LogContext> getLogHandler(String name) throws JrtcInvalidProcessorConfigurationException {
        IHandler<LogContext> handler = logHandlers.get(name);

        if (null == handler) {
            log.warn("** 无效的 eventHandler 名:" + name + " **");
            throw new JrtcInvalidProcessorConfigurationException("** 无效的 handler 名:" + name + " **");
        }

        return handler;
    }

    public IHandler<StateContext> getStateHandler(String name) throws JrtcInvalidProcessorConfigurationException {
        IHandler<StateContext> handler = stateHandlers.get(name);

        if (null == handler) {
            log.warn("** 无效的 eventHandler 名:" + name + " **");
            throw new JrtcInvalidProcessorConfigurationException("** 无效的 handler 名:" + name + " **");
        }

        return handler;
    }
}
