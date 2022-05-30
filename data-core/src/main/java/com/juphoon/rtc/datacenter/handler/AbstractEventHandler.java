package com.juphoon.rtc.datacenter.handler;

import com.juphoon.rtc.datacenter.api.EventContext;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
@Setter
@Getter
public abstract class AbstractEventHandler extends AbstractHandler<EventContext> {
}
