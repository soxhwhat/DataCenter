package com.juphoon.rtc.datacenter.cube.service;

import Common.ObjectServer;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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
    private EventCollectionServer eventCollectionServer;

    @Override
    public ObjectServer buildServiceServer() {
        return eventCollectionServer;
    }
}