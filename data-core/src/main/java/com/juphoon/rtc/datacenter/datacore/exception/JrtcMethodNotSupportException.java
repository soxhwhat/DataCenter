package com.juphoon.rtc.datacenter.datacore.exception;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    3/21/22 4:34 PM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */ 
public class JrtcMethodNotSupportException extends RuntimeException {
    public JrtcMethodNotSupportException(String message) {
        super(message);
    }
}
