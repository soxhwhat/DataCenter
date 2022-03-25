package com.juphoon.rtc.datacenter.cube.notice;

import Common.Exception;
import Common.ServerCall;
import com.juphoon.iron.component.threadpool.AbstractIronTask;
import com.juphoon.iron.component.threadpool.IronThreadPool;
import com.juphoon.rtc.datacenter.entity.notice.CubeNoticeResponse;
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
 **/
@Slf4j
@Component
public class NoticeEventServerImpl extends Event.NoticeEventServer {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private IronThreadPool ironThreadPool;

    @Override
    public void verCode_begin(ServerCall __call, Map<String, String> params) throws Exception {
        log.info("verCode_begin {}", params);
        ironThreadPool.submit(new AbstractIronTask() {
            @Override
            public void onRunning() {
                try {
                    deleteRoomIdSuffix(params);
                    deleteUserIdSuffix(params);
                    CubeNoticeResponse result = noticeService.verCode(params);

                    verCode_end(__call, true, result.getResult());

                } catch (java.lang.Exception e) {
                    __call.setReason(e.getMessage());
                    verCode_end(__call, false, new HashMap<>());
                }
            }
        });

    }

    @Override
    public void verJoinRoom_begin(ServerCall __call, Map<String, String> params) throws Exception {
        log.info("verJoinRoom_begin {}", params);
        ironThreadPool.submit(new AbstractIronTask() {

            @Override
            public void onRunning() {
                try {
                    deleteRoomIdSuffix(params);
                    deleteUserIdSuffix(params);
                    if (isCdUser(params)) {
                        roomNotice_end(__call, true, new HashMap<>());
                        return;
                    }
                    CubeNoticeResponse result = noticeService.verJoinRoom(params);
                    verJoinRoom_end(__call, true, result.getResult());
                } catch (java.lang.Exception e) {
                    __call.setReason(e.getMessage());
                    verJoinRoom_end(__call, false, new HashMap<>());
                }
            }
        });


    }

    @Override
    public void roomNotice_begin(ServerCall __call, Map<String, String> params) throws Exception {
        log.info("roomNotice_begin {}", params);
        ironThreadPool.submit(new AbstractIronTask() {

            @Override
            public void onRunning() {
                try {
                    deleteRoomIdSuffix(params);
                    deleteUserIdSuffix(params);
                    if (isCdUser(params)) {
                        roomNotice_end(__call, true, new HashMap<>());
                        return;
                    }
                    CubeNoticeResponse result = noticeService.roomNotice(params);
                    roomNotice_end(__call, true, result.getResult());
                } catch (java.lang.Exception e) {
                    __call.throwException(new Exception(e.getMessage()));
                }
            }

        });

    }

    @Override
    public void recordSnapshotNotice_begin(ServerCall __call, Map<String, String> params) throws Exception {
        log.info("recordSnapshotNotice_begin {}", params);
        ironThreadPool.submit(new AbstractIronTask() {

            @Override
            public void onRunning() {
                try {
                    deleteRoomIdSuffix(params);
                    deleteUserIdSuffix(params);
                    Map result = noticeService.recordSnapshotNotice(params);
                    recordSnapshotNotice_end(__call, true, result);
                } catch (java.lang.Exception e) {
                    log.error("e", e);
                    __call.setReason(e.getMessage());
                    recordSnapshotNotice_end(__call, false, null);
                }
            }

        });

    }

    @Override
    public void sendOnlineMessageAndNotice_begin(ServerCall __call, String receiverUri, Map<String, String> params) throws Exception {
        ironThreadPool.submit(new AbstractIronTask() {

            @Override
            public void onRunning() {
                try {
                    deleteRoomIdSuffix(params);
                    String sendUserUri = __call.getParam("account");
                    Map result = noticeService.sendOnlineMessageAndNotice(getUsername(sendUserUri), receiverUri, params);

                    sendOnlineMessageAndNotice_end(__call, true, result);
                } catch (java.lang.Exception e) {
                    log.error("e", e);
                    __call.setReason(e.getMessage());
                    sendOnlineMessageAndNotice_end(__call, false, null);
                }
            }

        });

    }

    @Override
    public void keepAlive_begin(ServerCall __call, int type, String username, Map<String, String> params) throws Exception {
        ironThreadPool.submit(new AbstractIronTask() {

            @Override
            public void onRunning() {
                try {
                    deleteRoomIdSuffix(params);
                    String uri = __call.getParam("account");
                    if (StringUtils.isEmpty(uri)) {
                        uri = username;
                    }

                    noticeService.keepAlive(type, getUsername(uri), username, params);

                    keepAlive_end(__call, true, new HashMap<>());
                } catch (java.lang.Exception e) {
                    log.error("e", e);
                    __call.setReason(e.getMessage());
                    keepAlive_end(__call, false, null);
                }

            }
        });
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

        }

    }
}
