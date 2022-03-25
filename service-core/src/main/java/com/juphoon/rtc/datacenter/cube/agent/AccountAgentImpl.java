package com.juphoon.rtc.datacenter.cube.agent;


import Common.AgentAsync;
import Common.CallParams;
import Common.IputStream;
import Common.ObjectAgent;
import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.iron.cube.starter.CubeApplication;
import com.juphoon.iron.cube.starter.CubeUtils;
import com.juphoon.rtc.datacenter.Account.AccountAgent;
import com.juphoon.rtc.datacenter.Account.SessionAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Juphoon on 2018/2/12.
 */
@Component
@Slf4j
public class AccountAgentImpl {

    private final CubeApplication cubeApplication;

    @Autowired
    public AccountAgentImpl(CubeApplication cubeApplication) {
        this.cubeApplication = cubeApplication;
    }

    private static final byte[] emptyByte = new byte[]{};


    public void sendImOnlineMessage(String uri, Map<String, String> params, Consumer<Boolean> consumer) {
        sendMessage(uri, "kMtcImOnlineMessage", params, consumer);
    }

    public void sendUserControlMessage(String uri, Map<String, String> params) {
        params.put("notify", "kMtcIm3rdServerOnlineMessage");
        sendMessage(uri, "kMtcIm3rdServerOnlineMessage", params, null);
    }


    public void sendMessage(String uri, String type, Map<String, String> params, Consumer<Boolean> consumer) {
        log.info("message send {}", IronJsonUtils.objectToJson(params));
        String main = Thread.currentThread().getName();
        String oid = "Account/" + uri;
        ObjectAgent agent = cubeApplication.getAgent(oid);
        AccountAgent.sendOnlineMessage_begin(agent, new AgentAsync() {
            @Override
            public void cmdResult(int rslt, IputStream iput, Object userdata) {
                boolean messageEnd = SessionAgent.sendOnlineMessage_end(rslt, iput);
                log.info("oid {} messageEnd {} main-{}  error-msg:{}", oid, messageEnd, main, ObjectAgent.getLastReason());
                if (consumer != null) {
                    consumer.accept(messageEnd);
                }
            }
        }, type, params, emptyByte, CubeUtils.newCallParams(), null);

    }


    public boolean sendMessage(String uri,Map<String, String> params) throws Exception {
       // log.info("message send {}", IronJsonUtils.objectToJson(params));
        String main = Thread.currentThread().getName();
        String oid = "Account/" + uri;
        ObjectAgent agent = cubeApplication.getAgent(oid);
        CallParams callParams = CubeUtils.newCallParams();
        callParams.setParam("Notify.Command", "3rdServerOnlineMsg");
        boolean ret = AccountAgent.sendOnlineMessage(agent, "kMtcImOnlineMessage", params, emptyByte, callParams);
        if (!ret) {
            throw new Exception(ObjectAgent.getLastReason());
        }
        return ret;
    }


    public void kick(String uri) {
        String oid = "Account/" + uri;
        log.info("kick {}", oid);
        ObjectAgent agent = cubeApplication.getAgent(oid);
        boolean ret = AccountAgent.kickOff(agent, "kick", CubeUtils.newCallParams());
        if (!ret) {
            log.error(ObjectAgent.getLastReason());
        }
    }


}
