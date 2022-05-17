package com.juphoon.rtc.datacenter.cube.service;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import DataCollection.LogCollectionServiceServer;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Setter
@Slf4j
@Component
@CubeService(serviceName = "#LogCollectionServer")
@SuppressWarnings("PMD")
public class CollectionLogServiceServerServiceImpl extends AbstractCubeService {

//    @Autowired
//    LogCollectionServerProcess logCollectionServerProcess;

    @Override
    public ObjectServer buildServiceServer() {
        return new LogCollectionServiceServer() {
            @Override
            public void log_begin(ServerCall __call, List<String> logList) throws Exception {
                log.info("日志上报:{}", logList.size());
//                logCollectionServerProcess.process(__call, logList);
            }

            @Override
            public void serverLog_begin(ServerCall __call, List<String> logList) throws Exception {
                log.info("日志上报:{}", logList.size());
//                logCollectionServerProcess.process(__call, logList);
            }
        };
    }
}

