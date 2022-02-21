package cloud.juphoon.jrtc.handler.inner;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.handler.AbstractCareAllEventHandler;
import cloud.juphoon.jrtc.mq.IEventQueueService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>通用最后处理句柄</p>
 * // 最后一个句柄需要处理
 * // 1. 判断是否已经完成处理，若已经完成，则从mq中确认（删除）
 * // 2. 日志等
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 5:06 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LastInnerEventHandler extends AbstractCareAllEventHandler {
    @Autowired
    private IEventQueueService queueService;

    @Override
    public boolean handle(EventContext ec) {
        // TODO

        queueService.processOk(ec);

        return true;
    }
}
