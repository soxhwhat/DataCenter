package com.juphoon.rtc.datacenter.servicecore.cube.service;

import Common.ObjectServer;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.DATA_CENTER_CONFIG_PREFIX;


@Setter
@Slf4j
@Component
@CubeService(serviceName = "#InfoIdCollectionServer")
@ConditionalOnProperty(prefix = DATA_CENTER_CONFIG_PREFIX, value = "log.enabled", havingValue = "true")
@SuppressWarnings("PMD")
public class LogInfoCollectionServiceServerImpl extends AbstractCubeService {
    @Autowired
    private LogInfoServiceServerImpl serviceServer;

    @Override
    public ObjectServer buildServiceServer() {
        return serviceServer;
    }
}

