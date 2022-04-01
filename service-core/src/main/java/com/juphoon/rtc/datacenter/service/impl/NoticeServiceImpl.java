/**
 * Copyright (C), 2005-2019, Juphoon Corporation
 * <p>
 * FileName   : NoticeServiceImpl
 * Author     : wenqiangdong
 * Date       : 2019-12-16 18:04
 * Description:
 * <p>
 * <p>
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.juphoon.rtc.datacenter.service.impl;


import com.juphoon.iron.component.utils.response.IronException;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.entity.notice.UserKeepAlive;
import com.juphoon.rtc.datacenter.property.NoticeProperties;
import com.juphoon.rtc.datacenter.service.CacheService;
import com.juphoon.rtc.datacenter.service.DataService;
import com.juphoon.rtc.datacenter.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.KEY_AGREE;

/**
 * <p>赞同通知实现类</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "notice.config.type", havingValue = "zt", matchIfMissing = true)
public class NoticeServiceImpl implements NoticeService {
    private static final String ROOM_ID = "roomid";
    private static final String APP_GUID = "appguid";
    private static final String USER_ID = "userid";
    private static final String USER_NAME = "username";
    private static final String RETURN_CODE = "return_code";
    private static final String RETURN_MSG = "return_msg";
    private static final String RETURN_CODE_FAIL = "FAIL";
    private static final String ERROR_CODE = "errorcode";


    private NoticeProperties noticeProperties;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private DataService dataService;

    @Autowired
    public NoticeServiceImpl(NoticeProperties noticeProperties) {
        this.noticeProperties = noticeProperties;
    }

    /// 登录请求属于业务核心逻辑，应该要同步接口实现，不宜使用异步方式 TODO
    ///

    @Override
    public void verCode(Map<String, String> params) {
        log.info("登录请求:{}", params);

        params.put(APP_GUID, noticeProperties.getAppguid());

        dataService.commit(buildContext(EventType.ROOM_NOTICE, params));
    }

    @Override
    public void verJoinRoom(Map<String, String> params) {
        log.info("登录通知:{}", params);

        params.put(APP_GUID, noticeProperties.getAppguid());

        if (isSecondRoom(params)) {
            log.debug("isSecondRoom");
            return;
        }

        dataService.commit(buildContext(EventType.VERIFY_JOIN, params));
    }

    @Override
    public void roomNotice(Map<String, String> params) {
        log.info("进出房间通知:{}", params);

        params.put(APP_GUID, noticeProperties.getAppguid());

        if (isSecondRoom(params)) {
            log.debug("isSecondRoom");
            return;
        }

        dataService.commit(buildContext(EventType.ROOM_NOTICE, params));
    }

    @Override
    public Map<String, String> recordSnapshotNotice(Map<String, String> params) {
        log.info("录制通知:{}", params);

        params.put(APP_GUID, noticeProperties.getAppguid());

        try {
            buildContext(EventType.SNAPSHOT_NOTICE, params);
        } catch (Exception e) {
            throw new IronException("通知失败");
        }
        return new HashMap<>(0);
    }

    @Override
    public Map<String, String> sendOnlineMessageAndNotice(String sendUserUri, String receiverUri, Map<String, String> params) {
        throw new UnsupportedOperationException("sendOnlineMessageAndNotice不再支持，由Portal重新实现");
    }

    @Override
    public void keepAlive(int type, String uri, String username, Map<String, String> params) {
        params.put(APP_GUID, noticeProperties.getAppguid());

        // uri = uri.split("@")[0].split(":")[1];
        params.put(USER_ID, uri);
        params.put(USER_NAME, uri);
        if (type == 0) {
            Object alive = cacheService.hGet(KEY_AGREE, uri);
            if (alive != null) {
                UserKeepAlive userKeepAlive = (UserKeepAlive) alive;
                userKeepAlive.setKeepAliveTime(System.currentTimeMillis());
                cacheService.hPut(KEY_AGREE, uri, userKeepAlive);
            } else {
                log.info("params {}", params);
                UserKeepAlive userKeepAlive = new UserKeepAlive();
                userKeepAlive.setKeepAliveTime(System.currentTimeMillis());
                userKeepAlive.setUserid(uri);
                userKeepAlive.setUsername(username);
                cacheService.hPut(KEY_AGREE, uri, userKeepAlive);

                dataService.commit(buildContext(EventType.LOGIN_EVENT, params));
            }
        } else if (type == 1) {
            cacheService.hRemove(KEY_AGREE, uri);

            params.put(ERROR_CODE, "0");

            dataService.commit(buildContext(EventType.LOGOUT_EVENT, params));
        }
    }

    private EventContext buildContext(EventType eventType, Map<String, String> params) {
        Event event = Event.builder().type(eventType.getType()).number(eventType.getNumber()).params(params).build();

        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        return eventContext;
    }

    private boolean isSecondRoom(Map<String, String> params) {
        if (params.containsKey(ROOM_ID)) {
            return params.get(ROOM_ID).lastIndexOf("_second") > 1;
        } else {
            return false;
        }
    }

    private boolean isSuccess(ResponseEntity<Map> httpEntity) {
        if (!httpEntity.getStatusCode().is2xxSuccessful()) {
            return false;
        }
        if (httpEntity.getBody().get(RETURN_CODE) != null && RETURN_CODE_FAIL.equals(httpEntity.getBody().get(RETURN_CODE))) {
            Object msg = httpEntity.getBody().get(RETURN_MSG);
            throw new IronException(msg == null ? "验证失败" : msg.toString());
        }
        return true;
    }

}
