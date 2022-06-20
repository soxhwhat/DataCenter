package com.juphoon.rtc.datacenter.cube.service;

import Common.Exception;
import Common.ServerCall;
import DataCollection.Info;
import DataCollection.InfoIdCollectionServiceServer;
import com.juphoon.iron.cube.starter.CubeUtils;
import com.juphoon.rtc.datacenter.api.LogContext;
import com.juphoon.rtc.datacenter.service.LogService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.codecs.IdGenerator;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.*;
import static com.juphoon.rtc.datacenter.api.EventType.CLIENT_LOG_INFO_EVENT;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/4/8 10:00
 * @Description:
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = DATA_CENTER_CONFIG_PREFIX, value = "log.enabled", havingValue = "true")
@SuppressWarnings("PMD")
public class LogInfoServiceServerImpl extends InfoIdCollectionServiceServer {

    @Autowired
    private LogService logService;

    @Override
    public void getInfoId_begin(ServerCall serverCall, Info info) throws Exception {
        boolean ret = true;
        String id = "";

        try {
            String magic = CubeUtils.bytesToHex(serverCall.getMagic());

            log.debug("magic:{}", magic);

            String host = serverCall.getParam("host");

            ObjectIdGenerator generator = new ObjectIdGenerator();
            id = generator.toString();

            LogContext context = new LogContext(Collections.singletonList(parseInfo(info, generator)));

            context.setEventType(CLIENT_LOG_INFO_EVENT);
            context.setRequestId(magic);
            context.setFrom(host);

            logService.commit(context);
        } catch (java.lang.Exception e) {
            ret = false;
            serverCall.setReason(e.getMessage());
            log.warn("", e);
        } finally {
            getInfoId_end(serverCall, ret, id);
        }
    }

    @SneakyThrows
    private String parseInfo(Info info, IdGenerator id) {
        Document document = new Document();

        log.debug("{}", info);

        Document sourceJson = new Document();
        sourceJson.put(TAGS, info.tags);
        sourceJson.put(SOURCE_INFO_KEY, Document.parse(info.params));

        document.put(ID_KEY, id.generate());
        document.put(VERSION_KEY, VERSION_VALUE);
        document.put(UNDERLINE_TIMESTAMP, info.timestamp);
        document.put(VERSION_SOURCE, sourceJson);
        document.put(TYPE, info.type);

        return document.toJson();
    }
}
