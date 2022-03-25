package com.juphoon.rtc.datacenter.service;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.processor.IEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 17:59
 * @Description:
 */
@Slf4j
public class DataService {
    private List<IEventProcessor> processors;

    public DataService(List<IEventProcessor> processors) {
        assert null != processors : "processors 不能为空";

        this.processors = processors;
    }

    /**
     * 提交任务
     *
     * @param ec
     * @throws Exception
     */
    public void commit(EventContext ec) {
        log.debug("commit ec:{}", ec);

        for (IEventProcessor processor : processors) {
            log.debug("{} process ec:{}", processor.getName(), ec.body());

            /// 若是重做消息且处理器ID匹配
            if (ec.isRedoEvent() && ec.getProcessorId().equals(processor.getId())) {
                processor.process(ec);
                break;
            }
            /// 若是新消息，则需要复制一份
            else {
                EventContext copy = new EventContext();
                BeanUtils.copyProperties(ec, copy);
                copy.setProcessorId(processor.getId());
                ///
                processor.process(copy);
            }
        }
    }

    /**
     * 批量提交
     *
     * @param contexts
     */
    public void commit(List<EventContext> contexts) {
        contexts.forEach(this::commit);
    }

}
