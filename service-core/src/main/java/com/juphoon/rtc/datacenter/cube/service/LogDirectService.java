package com.juphoon.rtc.datacenter.cube.service;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import DataCollection.LogCollectionServiceServer;
import com.juphoon.iron.cube.starter.AbstractCubeDirect;
import com.juphoon.iron.cube.starter.CubeUtils;
import com.juphoon.iron.cube.starter.annotation.CubeDirect;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.service.LogService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.*;


@Setter
@Slf4j
@Component
@CubeDirect(serviceName = "LogDirectServer", endPointsValue = "sarc;")
@SuppressWarnings("PMD")
public class LogDirectService extends AbstractCubeDirect {

    @Autowired
    private LogService logService;

    @Override
    public ObjectServer buildServiceServer() {
        return new LogCollectionServiceServer() {
            @Override
            public void log_begin(ServerCall serverCall, List<String> logs) throws Exception {
                log.info("终端日志上报:{}", logs.size());

                log_end(serverCall, process(serverCall, logs, EventType.CLIENT_LOG_EVENT));
            }

//            @Override
//            public void serverLog_begin(ServerCall serverCall, List<String> logs) throws Exception {
//                log.info("服务端日志上报:{}", logs.size());
//
//                serverLog_end(serverCall, process(serverCall, logs, EventType.SERVER_LOG_EVENT));
//            }
        };
    }

    private boolean process(ServerCall serverCall, List<String> logs, EventType eventType) {
        boolean ret = true;

        try {
            assert CollectionUtils.isEmpty(logs) : "日志为空";

            String magic = CubeUtils.bytesToHex(serverCall.getMagic());

            log.debug("magic:{}", magic);

            String host = serverCall.getParam("host");

            LogContext context = new LogContext();

            context.setEventType(eventType);
            context.setRequestId(magic);
            context.setFrom(host);
            context.setLogs(parseLog(logs));

            logService.commit(context);
        } catch (java.lang.Exception e) {
            log.warn("process fail", e);
            ret = false;

        }

        return ret;
    }

    private List<String> parseLog(List<String> logs) {
        return logs.stream().map(data -> {

            log.debug(data);

            JSONObject logObject = new JSONObject();
            try {
                JSONObject sourceLog = new JSONObject(data);
                Long timestamp = sourceLog.getLong(TIMESTAMP);
                sourceLog.remove(TIMESTAMP);
                logObject.put(UNDERLINE_TIMESTAMP, timestamp);
                logObject.put(UNDERLINE_VERSION_KEY, UNDERLINE_VERSION_VALUE);
                logObject.put(VERSION_SOURCE, sourceLog);
            } catch (JSONException e) {
                log.error("日志记录错误:{}", data);
                return null;
            }
            return logObject.toString();
        }).collect(Collectors.toList());
    }
}

