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
import com.juphoon.rtc.datacenter.api.ProcessorId;
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
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.KEY_AGREE;

/**
 * <p>在开始处详细描述作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ke.wang@juphoon.com
 * @date   2022/3/25
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]       
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
    DataService dataService;


    @Autowired
    public NoticeServiceImpl(NoticeProperties noticeProperties) {
        log.info("noticeProperties:{}", noticeProperties);
        this.noticeProperties = noticeProperties;

    }


    @Override
    public CubeNoticeResponse verCode(Map<String, String> params) {
        params.put("appguid", noticeProperties.getAppguid());
        EventContext eventContext = buildContext(Event
                .builder()
                .type(25)
                .number(1)
                .params(convertToObjectMap(params))
                .build());
        try {
            dataService.commit(eventContext);
        } catch (Exception e) {
            throw new IronException("验证失败");
        }
        return buildResponse();

    }



    @Override
    public CubeNoticeResponse verJoinRoom(Map<String, String> params) {
        params.put("appguid", noticeProperties.getAppguid());
        if (isSecondRoom(params)) {
            return new CubeNoticeResponse(true, new HashMap<>());
        }
        try {
            dataService.commit(buildContext(Event
                    .builder()
                    .type(25)
                    .number(2)
                    .params(convertToObjectMap(params))
                    .build()));
        } catch (Exception e) {
            throw new IronException("验证失败");
        }
        return buildResponse();
    }

    @Override
    public CubeNoticeResponse roomNotice(Map<String, String> params) {
        params.put("appguid", noticeProperties.getAppguid());
        log.info("进出房间通知，{}", params);

        if (isSecondRoom(params)) {
            return new CubeNoticeResponse(true, new HashMap<>());
        }
        try {
            dataService.commit(buildContext(Event
                    .builder()
                    .type(25)
                    .number(3)
                    .params(convertToObjectMap(params))
                    .build()));
        } catch (Exception e) {
            throw new IronException("通知失败");
        }
        return buildResponse();

    }

    @Override
    public Map<String, String> recordSnapshotNotice(Map<String, String> params) {
        params.put("appguid", noticeProperties.getAppguid());
        log.info("通知录制文件:{}", params);
        try {
            dataService.commit(buildContext(Event
                    .builder()
                    .type(25)
                    .number(4)
                    .params(convertToObjectMap(params))
                    .build()));
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
                String databuf = Base64Utils.encodeToString(json.getBytes());
                transbufferReq.setAppguid(noticeProperties.getAppguid());
                transbufferReq.setDatesize(String.valueOf(databuf.length()));
                transbufferReq.setUserid(sendUserUri);
                transbufferReq.setUsername(sendUserUri);
                transbufferReq.setDatabuf(databuf);
                log.info("send notice..... {}", receiverUri);
                try {
                    dataService.commit(buildContext(Event
                            .builder()
                            .type(25)
                            .number(5)
                            .params(transbufferReq.convertToMap())
                            .build()));
                } catch (Exception e) {
                    throw new IronException("通知失败");
                }
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
                try {
                    dataService.commit(buildContext(Event
                            .builder()
                            .type(25)
                            .number(7)
                            .params(convertToObjectMap(params))
                            .build()));
                } catch (Exception e) {
                    throw new IronException("登录失败");
                }
            }
        } else if (type == 1) {
            cacheService.hRemove(KEY_AGREE, uri);
            params.put("errorcode", "0");
            try {
                dataService.commit(buildContext(Event
                        .builder()
                        .type(25)
                        .number(6)
                        .params(convertToObjectMap(params))
                        .build()));
            } catch (Exception e) {
                throw new IronException("登录失败");
            }
        }
    }

    public EventContext buildContext(Event event) {
        EventContext eventContext = new EventContext();
        eventContext.setProcessorId(ProcessorId.AGREE.getId());
        eventContext.setEvent(event);
        return eventContext;
    }

    public CubeNoticeResponse buildResponse() {
        HashMap<String, String> map = new HashMap<>();
        map.put("return_code", "SUCCESS");
        map.put("return_msg", "");
        return new CubeNoticeResponse(true, map);
    }

    public Map<String, Object> convertToObjectMap(Map<String, String> stringMap) {
        Map<String, Object> objectMap = new HashMap<>();
        stringMap.forEach((k, v) -> {
            objectMap.put(k, v);
        });
        return objectMap;
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
