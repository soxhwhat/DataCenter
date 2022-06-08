package com.juphoon.rtc.datacenter.processor.queue;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    6/8/22 1:57 PM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Getter
@Setter
public class ContextHolder<T> {
    private T context;
}
