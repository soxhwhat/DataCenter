package com.juphoon.rtc.datacenter.factory;

import com.juphoon.rtc.datacenter.event.queue.IEventQueueService;
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
public class EventQueueFactory {

    private Set<String> supportQueueService = new HashSet<>(Arrays.asList("none", "disruptor"));

    /**
     * 通过传入的 storage 名称生成对应的 storage 实例
     * @param name
     * @return
     */
    public IEventQueueService getEventQueueService(String name) throws JrtcInvalidProcessorConfigurationException {

        if (!supportQueueService.contains(name)) {
            log.warn("** 无效的 queueService 类型:" + name + " **");
            throw new JrtcInvalidProcessorConfigurationException("** 无效的 queueService 类型:" + name + ", 请检查配置 **");
        }

        // todo
        return null;
    }
}
