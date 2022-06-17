package com.juphoon.rtc.datacenter.cube.service;

import Common.ObjectServer;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Setter
@Slf4j
@Component
@CubeService(serviceName = "#InfoIdCollectionServer")
@SuppressWarnings("PMD")
public class LogInfoCollectionServiceServerImpl extends AbstractCubeService {
    @Autowired
    private LogInfoServiceServerImpl serviceServer;

    @Override
    public ObjectServer buildServiceServer() {
        return serviceServer;
    }
}

