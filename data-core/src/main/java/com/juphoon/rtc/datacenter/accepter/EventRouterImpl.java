package com.juphoon.rtc.datacenter.accepter;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 15:00
 * @Description:
 */
@Service
public class EventRouterImpl implements IEventRouter {
    /**
     * 由service模块注入
     */
    @Autowired
    private DataService dataService;

    /**
     * 把消息发送到各个process中 一条消息通过多个process
     * 若抛异常，则表示该消息消费异常，需要将错误返回给事件生产方
     *
     * @param contexts
     */
    @Override
    public void router(List<EventContext> contexts) {
        assert null != dataService : "dataService 未初始化!";

        dataService.commit(contexts);
    }
}
