package cloud.juphoon.jrtc.handler.test;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.handler.AbstractCareAllEventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 6:30 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public class Ahandler extends AbstractCareAllEventHandler {
    @Override
    public boolean handle(EventContext ec) {
        log.info("handle");
        return true;
    }
}
