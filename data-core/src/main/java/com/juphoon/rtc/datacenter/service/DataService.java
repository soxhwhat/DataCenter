package com.juphoon.rtc.datacenter.service;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.processor.IEventProcessor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 17:59
 * @Description:
 */
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
        for (IEventProcessor processor : processors) {
            EventContext eventContext = new EventContext();
            BeanUtils.copyProperties(ec,eventContext);
            processor.process(eventContext);
        }
    }

}
