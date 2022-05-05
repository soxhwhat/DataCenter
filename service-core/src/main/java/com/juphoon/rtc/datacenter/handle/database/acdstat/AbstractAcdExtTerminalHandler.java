package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdCommonPO;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

import static com.juphoon.rtc.datacenter.api.EventType.TICKER_EVENT_WAIT;

/**
 * <p>jrtc_acd_ext_terminal_xxxx表的抽象handler类</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/5/5
 */
@Slf4j
public abstract class AbstractAcdExtTerminalHandler<T extends AcdCommonPO> extends AbstractAcdStatHandler<T> {

    @Override
    public boolean handle(EventContext ec, T po) throws Exception {
        // 非正常结束不处理
        if (ec.getEvent().isEndWithException()) {
            return true;
        }
        return super.handle(ec, po);
    }

    @Override
    public List<EventType> careEvents() {
        return Collections.singletonList(TICKER_EVENT_WAIT);
    }

}
