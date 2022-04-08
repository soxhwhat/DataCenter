package com.juphoon.rtc.datacenter.cube.service;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import DataCollection.EventCollectionServiceServer;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>EventCollectionServer RPC服务实现</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2022-03-25
 */
@Slf4j
@CubeService(serviceName = "#EventCollectionServer")
@SuppressWarnings("PMD")
public class EventCollectionServiceServerImpl extends AbstractCubeService {

    @Autowired
    EventCollectionServerProcess eventCollectionServer;

    @Override
    public ObjectServer buildServiceServer() {
        return new EventCollectionServiceServer() {
            @Override
            public void event_begin(ServerCall serverCall, List<DataCollection.Event> eventList) throws Exception {
                event_end(serverCall, eventCollectionServer.process(serverCall, eventList));
            }

            @Override
            public void event2_begin(ServerCall serverCall, String topic, List<DataCollection.Event> eventList) throws Exception {
                event_end(serverCall, eventCollectionServer.process(serverCall, eventList));
            }

            @Override
            public boolean subEvent(ServerCall serverCall) throws Exception {
                return false;
            }
        };
    }
}