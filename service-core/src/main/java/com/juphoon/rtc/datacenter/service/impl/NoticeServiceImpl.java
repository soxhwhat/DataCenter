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


import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.iron.component.utils.response.IronException;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.cube.agent.AccountAgentImpl;
import com.juphoon.rtc.datacenter.entity.notice.CubeNoticeResponse;
import com.juphoon.rtc.datacenter.entity.notice.TransbufferReq;
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
import org.springframework.util.Base64Utils;

import java.util.HashMap;
import java.util.Map;

import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.KEY_AGREE;

/**
 * <p>在开始处详细描述作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "notice.config.type", havingValue = "zt", matchIfMissing = true)
public class NoticeServiceImpl implements NoticeService {

    private NoticeProperties noticeProperties;

    @Autowired
    private AccountAgentImpl sessionAgent;

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

        params.put("appguid", noticeProperties.getAppguid());

        dataService.commit(buildContext(25, 3, params));
    }

    /// TODO 所有 type/number 都改为宏/常量

    @Override
    public void verJoinRoom(Map<String, String> params) {
        log.info("登录通知:{}", params);

        params.put("appguid", noticeProperties.getAppguid());

        if (isSecondRoom(params)) {
            log.debug("isSecondRoom");
            return;
        }

        dataService.commit(buildContext(25, 2, params));
    }

    @Override
    public void roomNotice(Map<String, String> params) {
        log.info("进出房间通知:{}", params);

        params.put("appguid", noticeProperties.getAppguid());

        if (isSecondRoom(params)) {
            log.debug("isSecondRoom");
            return;
        }

        dataService.commit(buildContext(25, 3, params));
    }

    @Override
    public Map<String, String> recordSnapshotNotice(Map<String, String> params) {
        log.info("录制通知:{}", params);

        params.put("appguid", noticeProperties.getAppguid());

        try {
            buildContext(25, 4, params);
        } catch (Exception e) {
            throw new IronException("通知失败");
        }
        return new HashMap<>();
    }

    @Override
    public Map<String, String> sendOnlineMessageAndNotice(String sendUserUri, String receiverUri, Map<String, String> params) {
        sessionAgent.sendImOnlineMessage(receiverUri, params, ret -> {
            if (ret) {
                TransbufferReq transbufferReq = new TransbufferReq();
                String json = IronJsonUtils.objectToJson(params);
                String dataBuf = Base64Utils.encodeToString(json.getBytes());
                transbufferReq.setAppguid(noticeProperties.getAppguid());
                transbufferReq.setDatesize(String.valueOf(dataBuf.length()));
                transbufferReq.setUserid(sendUserUri);
                transbufferReq.setUsername(sendUserUri);
                transbufferReq.setDatabuf(dataBuf);
                log.info("send notice..... {}", receiverUri);

                ///
                dataService.commit(buildContext(25, 5, params));
            }
        });
        return new HashMap<>();
    }


    @Override
    public void keepAlive(int type, String uri, String username, Map<String, String> params) {
        params.put("appguid", noticeProperties.getAppguid());

        // uri = uri.split("@")[0].split(":")[1];
        params.put("userid", uri);
        params.put("username", uri);
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

                dataService.commit(buildContext(25, 7, params));
            }
        } else if (type == 1) {
            cacheService.hRemove(KEY_AGREE, uri);

            params.put("errorcode", "0");

            dataService.commit(buildContext(25, 6, params));
        }
    }

    private EventContext buildContext(Integer type, Integer number, Map<String, String> params) {
        Event event = Event.builder().type(type).number(number).params(params).build();

        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        return eventContext;
    }

    private boolean isSecondRoom(Map<String, String> params) {
        if (params.containsKey("roomid")) {
            return params.get("roomid").lastIndexOf("_second") > 1;
        } else {
            return false;
        }
    }

    private boolean isSuccess(ResponseEntity<Map> httpEntity) {
        if (!httpEntity.getStatusCode().is2xxSuccessful()) {
            return false;
        }
        if (httpEntity.getBody().get("return_code") != null && "FAIL".equals(httpEntity.getBody().get("return_code"))) {
            Object msg = httpEntity.getBody().get("return_msg");
            throw new IronException(msg == null ? "验证失败" : msg.toString());
        }
        return true;
    }

}
