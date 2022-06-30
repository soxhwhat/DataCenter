package com.juphoon.rtc.datacenter.datacore.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * <p>HTTP响应</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/21/22 2:57 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Getter
@Setter
public class JrtcHttpResponse<T> {
    /**
     * http 响应码
     */
    private HttpStatus httpStatus;

    private HttpHeaders headers;

    private T body;
}
