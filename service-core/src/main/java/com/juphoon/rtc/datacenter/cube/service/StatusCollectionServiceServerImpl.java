package com.juphoon.rtc.datacenter.cube.service;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import DataCollection.FlowStatus;
import DataCollection.FlowStatusJson;
import DataCollection.StatusCollectionServiceServer;
import com.juphoon.iron.component.threadpool.AbstractIronTask;
import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
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
public class StatusCollectionServiceServerImpl  extends AbstractCubeService {

    @Autowired
    private StatusCollectionServerProcess serverProcess;


    @Override
    public ObjectServer buildServiceServer() {

        return new StatusCollectionServiceServer() {

            @Override
            public void putStatusListJson2_begin(ServerCall call, String topic, List<FlowStatusJson> flowList) throws Exception {
                log.info("状态数据上报:{}",flowList);
                putStatusListJson2_end(call, serverProcess.process(call, flowList));
            }

            @Override
            public void putStatusJson2_begin(ServerCall call, String topic, FlowStatusJson flow) throws Exception {
                log.info("状态数据上报:{}",flow);
                putStatusJson2_end(call, serverProcess.process(call, Arrays.asList(flow)));
            }
        };
    }
}

