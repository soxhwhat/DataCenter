package cloud.juphoon.jrtc.accepter;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.processor.IEventProcessor;
import cloud.juphoon.jrtc.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     *
     * 把消息发送到各个process中 一条消息通过多个process
     * 若抛异常，则表示该消息消费异常，需要将错误返回给事件生产方
     * @param event
     */
    @Override
    public void router(EventContext event) throws Exception {
        dataService.commit(event);
    }

}
