package cloud.juphoon.jrtc.handler.inner;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.handler.AbstractCareAllEventHandler;

/**
 * <p>通用第一个处理句柄</p>
 * // 最后一个句柄需要处理
 * // 1. 判断是否已经完成处理，若已经完成，则从mq中确认（删除）
 * // 2. 日志等
 *
 * @author  ajian.zheng@juphoon.com
 * @date    2/18/22 5:06 PM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */ 
public class FirstInnerEventHandler extends AbstractCareAllEventHandler {
    @Override
    public boolean handle(EventContext ec) {
        // TODO
        ec.handle();

        return true;
    }
}
