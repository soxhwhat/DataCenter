package com.juphoon.rtc.datacenter.factory;

import com.juphoon.rtc.datacenter.event.storage.IEventLogService;
import com.juphoon.rtc.datacenter.event.storage.IRedoLogService;
import com.juphoon.rtc.datacenter.exception.JrtcInvalidProcessorConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>handler 工厂</p>
 *
 * 同 processor 工厂
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/16/22 8:54 AM
 */
@Component
@Slf4j
public class EventStorageFactory {

    private Set<String> supportStorage = new HashSet<>(Arrays.asList("memory", "sqlite"));

    /**
     * 通过传入的 storage 名称生成对应的 storage 实例
     * @param name
     * @return
     */
    public IEventLogService getEventLogService(String name) throws JrtcInvalidProcessorConfigurationException {

        if (!supportStorage.contains(name)) {
            log.warn("** 无效的 event-log 类型:" + name + " **");
            throw new JrtcInvalidProcessorConfigurationException("** 无效的 event-log 类型:" + name + ", 请检查配置 **");
        }

        // todo
        return null;
    }

    /**
     * 通过传入的 storage 名称生成对应的 storage 实例
     * @param name
     * @return
     */
    public IRedoLogService getRedoLogService(String name) throws JrtcInvalidProcessorConfigurationException {

        if (!supportStorage.contains(name)) {
            log.warn("** 无效的 redo-log 类型:" + name + " **");
            throw new JrtcInvalidProcessorConfigurationException("** 无效的 redo-log 类型:" + name + ", 请检查配置 **");
        }

        // todo
        return null;
    }
}
