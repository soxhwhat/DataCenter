package com.juphoon.rtc.datacenter.factory;

import com.juphoon.rtc.datacenter.exception.JrtcInvalidProcessorConfigurationException;
import com.juphoon.rtc.datacenter.handler.IEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
    private Map<String, IEventHandler> handlers;

    @PostConstruct
    public void init() {
        assert null != handlers : "** handler 不能为空 **";
        log.info("handlers size:{}", handlers.size());
    }

    /**
     * 通过传入的processor名称获取对应的process实例
     * @param name
     * @return
     */
    public IEventHandler getHandler(String name) throws JrtcInvalidProcessorConfigurationException {
        IEventHandler handler = handlers.get(name);

        if (null == handler) {
            log.warn("** 无效的 handler 名:" + name + " **");
            throw new JrtcInvalidProcessorConfigurationException("** 无效的 handler 名:" + name + " **");
        }

        return handler;
    }
}
