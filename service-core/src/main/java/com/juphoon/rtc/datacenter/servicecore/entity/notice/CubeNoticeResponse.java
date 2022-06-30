/**
 * Copyright (C), 2005-2019, Juphoon Corporation
 * <p>
 * FileName   : CubeNoticeResponse
 * Author     : wenqiangdong
 * Date       : 2019-12-17 16:10
 * Description:
 * <p>
 * <p>
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.juphoon.rtc.datacenter.servicecore.entity.notice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @author wenqiangdong
 * @date 2019-12-17
 */
@Data
@AllArgsConstructor
public class CubeNoticeResponse {

    private boolean ret;
    private Map<String, String> result;

    public CubeNoticeResponse() {
        this.result = new HashMap<>();
        this.ret = false;
    }
}
