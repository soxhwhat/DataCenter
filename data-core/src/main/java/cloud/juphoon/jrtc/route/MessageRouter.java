package cloud.juphoon.jrtc.route;

import cloud.juphoon.jrtc.accepter.EventContent;
import cloud.juphoon.jrtc.processor.AbstractMessageProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/8 17:41
 * @Description:
 */
@Service
public class MessageRouter {

    //由service模块注入
    static List<AbstractMessageProcess> processList;

    /**
     *
     * 把消息发送到各个process中 一条消息通过多个process
     * @param eventContent
     */
    public void route(EventContent eventContent) {

    }

}
