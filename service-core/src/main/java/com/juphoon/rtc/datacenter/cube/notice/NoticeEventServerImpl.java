package com.juphoon.rtc.datacenter.cube.notice;

import Common.Exception;
import Common.ServerCall;
import com.juphoon.rtc.datacenter.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rongbin.huang
 * @create 2019-12-16 4:38 PM
 *
 * @update 1. 2022-03-25 ke.wang 移植
 * @update 2. 2022-03-28 ajian.zheng 去除线程池依赖
 *
 **/
@Slf4j
@Component
@SuppressWarnings("PMD")
public class NoticeEventServerImpl extends Event.NoticeEventServer {

    @Autowired
    private NoticeService noticeService;

    @Override
    public void verCode_begin(ServerCall serverCall, Map<String, String> params) throws Exception {
        log.info("verCode_begin {}", params);

        boolean ret = true;

        try {
            deleteRoomIdSuffix(params);
            deleteUserIdSuffix(params);

            noticeService.verCode(params);
        } catch (java.lang.Exception e) {
            log.warn("e", e);
            serverCall.setReason(e.getMessage());
            ret = false;
        }

        verCode_end(serverCall, ret, null);
    }

    @Override
    public void verJoinRoom_begin(ServerCall serverCall, Map<String, String> params) throws Exception {
        log.info("verJoinRoom_begin {}", params);

        boolean ret = true;

        try {
            deleteRoomIdSuffix(params);
            deleteUserIdSuffix(params);

            /// cd 不通知
            if (isCdUser(params)) {
                roomNotice_end(serverCall, true, new HashMap<>());
                return;
            }

            noticeService.verJoinRoom(params);
        } catch (java.lang.Exception e) {
            log.warn("e", e);
            serverCall.setReason(e.getMessage());
            ret = false;
        }

        verJoinRoom_end(serverCall, ret, null);
    }

    @Override
    public void roomNotice_begin(ServerCall serverCall, Map<String, String> params) throws Exception {
        log.info("roomNotice_begin {}", params);

        boolean ret = true;

        try {
            deleteRoomIdSuffix(params);
            deleteUserIdSuffix(params);
            if (isCdUser(params)) {
                roomNotice_end(serverCall, true, new HashMap<>());
                return;
            }

            noticeService.roomNotice(params);
        } catch (java.lang.Exception e) {
            log.warn("e", e);
            serverCall.setReason(e.getMessage());
            ret = false;
        }

        roomNotice_end(serverCall, ret, null);
    }

    @Override
    public void recordSnapshotNotice_begin(ServerCall serverCall, Map<String, String> params) throws Exception {
        log.info("recordSnapshotNotice_begin {}", params);

        boolean ret = true;

        try {
            deleteRoomIdSuffix(params);
            deleteUserIdSuffix(params);
            noticeService.recordSnapshotNotice(params);
        } catch (java.lang.Exception e) {
            log.warn("e", e);
            serverCall.setReason(e.getMessage());
            ret = false;
        }

        recordSnapshotNotice_end(serverCall, ret, null);
    }

    @Override
    public void sendOnlineMessageAndNotice_begin(ServerCall serverCall, String receiverUri, Map<String, String> params) throws Exception {
        log.info("sendOnlineMessageAndNotice_begin {}", params);

        boolean ret = true;

        try {
            deleteRoomIdSuffix(params);

            String sendUserUri = serverCall.getParam("account");

            noticeService.sendOnlineMessageAndNotice(getUsername(sendUserUri), receiverUri, params);
        } catch (java.lang.Exception e) {
            log.error("e", e);
            serverCall.setReason(e.getMessage());
            ret = false;
        }

        sendOnlineMessageAndNotice_end(serverCall, ret, null);
    }

    @Override
    public void keepAlive_begin(ServerCall serverCall, int type, String username, Map<String, String> params) throws Exception {
        log.info("sendOnlineMessageAndNotice_begin {}", params);

        boolean ret = true;

        try {
            deleteRoomIdSuffix(params);
            String uri = serverCall.getParam("account");
            if (StringUtils.isEmpty(uri)) {
                uri = username;
            }

            noticeService.keepAlive(type, getUsername(uri), username, params);

        } catch (java.lang.Exception e) {
            log.error("e", e);
            serverCall.setReason(e.getMessage());
            ret = false;
        }

        keepAlive_end(serverCall, ret, null);
    }


    private void deleteUserIdSuffix(Map<String, String> params) {
        try {
            if (params.containsKey("userid")) {
                String userid = params.get("userid");
                userid = getUsername(userid);
                params.put("userid", userid);
                params.put("username", userid);
            }
        } catch (java.lang.Exception e) {
            /// TODO，异常应该有后续，异常后该如何处理呢
        }
    }

    private String getUsername(String urluser) {
        if (urluser.indexOf("username:") == -1 && urluser.indexOf('@') == -1) {
            return urluser;
        }
        try {
            return urluser.substring(urluser.indexOf("username:") + "username:".length(), urluser.indexOf('@'));
        } catch (java.lang.Exception e) {
            return urluser;
        }
    }

    private boolean isCdUser(Map<String, String> params) {
        log.info("_call 验证是否是delivery_ params:{}", params);
        if (params.containsKey("userid")) {
            return params.get("userid").contains("delivery_");
        } else {
            return false;
        }
    }

    private void deleteRoomIdSuffix(Map<String, String> params) {
        try {
            if (params.containsKey("roomid")) {
                String roomid = params.get("roomid");
                String domainId = params.get("domainId");
                String appId = params.get("appId");
                roomid = roomid.replace(domainId + "_" + appId, "");
                params.put("roomid", roomid);
            }
        } catch (java.lang.Exception e) {
            /// TODO，异常应该有后续，异常后该如何处理呢
        }

    }
}
