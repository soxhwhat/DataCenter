package com.juphoon.rtc.datacenter.cube.service;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import DataCollection.FlowStatus;
import DataCollection.FlowStatusJson;
import DataCollection.StatusCollectionServiceServer;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.CubeUtils;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import com.juphoon.rtc.datacenter.api.State;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.service.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/4/27 13:40
 * @Description:
 */
@Slf4j
@Service
@CubeService(serviceName = "#StatusCollectionServer")
@SuppressWarnings("PMD")
public class StatusCollectionServiceServerImpl extends AbstractCubeService {

    @Autowired
    private StateService stateService;

    @Override
    public ObjectServer buildServiceServer() {
        return new StatusCollectionServiceServer() {
            @Override
            public void putStatus_begin(ServerCall serverCall, FlowStatus statusJson) throws Exception {
                log.error("不该调用 putStatus_begin");
                serverCall.setReason("Deprecated");
                putStatus_end(serverCall, false);
            }

            @Override
            public void endStatus_begin(ServerCall serverCall, FlowStatus statusJson) throws Exception {
                log.error("不该调用 endStatus_begin");
                serverCall.setReason("Deprecated");
                endStatus_end(serverCall, false);
            }

            @Override
            public void endStatusJson_begin(ServerCall serverCall, FlowStatusJson statusJson) throws Exception {
                log.error("不该调用 endStatusJson_begin");
                serverCall.setReason("Deprecated");
                endStatusJson_end(serverCall, false);
            }

            @Override
            public void putStatusJson_begin(ServerCall serverCall, FlowStatusJson statusJson) throws Exception {
                putStatusJson_end(serverCall, process(serverCall, stateService, statusJson));
            }

            @Override
            public void putStatusJson2_begin(ServerCall serverCall, String topic, FlowStatusJson statusJson) throws Exception {
                putStatusJson2_end(serverCall, process(serverCall, stateService, statusJson));
            }

            @Override
            public void putStatusListJson2_begin(ServerCall serverCall, String topic, List<FlowStatusJson> statusJsons) throws Exception {
                putStatusListJson2_end(serverCall, process(serverCall, stateService, statusJsons));
            }
        };
    }

    private boolean process(ServerCall serverCall, StateService service, FlowStatusJson statusJson) {
        boolean ret = true;
        try {
            String magic = CubeUtils.bytesToHex(serverCall.getMagic());

            log.debug("magic:{}", magic);

            String host = serverCall.getParam("host");

            StateContext context = new StateContext();

            context.setRequestId(magic);
            context.setFrom(host);
            context.setState(trans(statusJson));

            service.commit(context);
        } catch (java.lang.Exception e) {
            log.warn("process fail", e);
            serverCall.setReason("process fail:" + e.getMessage());
            ret = false;
        }

        return ret;
    }

    private boolean process(ServerCall serverCall, StateService service, List<FlowStatusJson> statusJsons) {
        boolean ret = true;
        try {
            String magic = CubeUtils.bytesToHex(serverCall.getMagic());

            log.debug("magic:{}", magic);

            String host = serverCall.getParam("host");

            List<StateContext> contexts = new ArrayList<>();
            for (FlowStatusJson statusJson : statusJsons) {
                StateContext context = new StateContext();

                context.setRequestId(magic);
                context.setFrom(host);
                context.setState(trans(statusJson));

                contexts.add(context);
            }

            service.commit(contexts);
        } catch (java.lang.Exception e) {
            log.warn("process fail", e);
            serverCall.setReason("process fail:" + e.getMessage());
            ret = false;
        }

        return ret;
    }

    private State trans(FlowStatusJson from) {
        return State.builder()
                .domainId((int) from.domainId)
                .appId((int) from.appId)
                .type(from.type)
                .state(from.status)
                .params(from.params)
                .uuid(from.uniqueId)
                .build();
    }

}

