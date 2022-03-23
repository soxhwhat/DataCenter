package com.juphoon.rtc.datacenter.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * <p>HTTP请求</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/21/22 2:57 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Getter
@Setter
public class JrtcHttpRequest<T> {
    /**
     * 目标uri
     */
    private String uri;

    /**
     * http 方法
     */
    private HttpMethod httpMethod;

    /**
     * http 请求头
     */
    private HttpHeaders headers;

    /**
     * http body
     */
    private T body;
}
