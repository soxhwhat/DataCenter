/**
 * Copyright (C), 2005-2019, Juphoon Corporation
 * <p>
 * FileName   : NoticeService
 * Author     : wenqiangdong
 * Date       : 2019-12-16 17:59
 * Description:
 * <p>
 * <p>
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.juphoon.rtc.datacenter.servicecore.service;


import java.util.Map;

/**
 * <p>赞同通知服务抽象接口</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@SuppressWarnings("PMD")
public interface NoticeService {

    void verCode(Map<String, String> params);

    void verJoinRoom(Map<String, String> params);

    void roomNotice(Map<String, String> params);

    Map<String, String> recordSnapshotNotice(Map<String, String> params);

    Map<String, String> sendOnlineMessageAndNotice(String sendUserUri, String receiverUri, Map<String, String> params);

    void keepAlive(int type, String uri, String username, Map<String, String> params);
}
