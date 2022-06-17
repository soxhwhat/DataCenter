package com.juphoon.rtc.datacenter.cube.service;

import Common.ObjectServer;
import com.juphoon.iron.cube.starter.AbstractCubeDirect;
import com.juphoon.iron.cube.starter.annotation.CubeDirect;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author zhiwei.zhai
 * @date   2022.05.31
 */


@Setter
@Slf4j
@Component
@CubeDirect(serviceName = "InfoDirectService",endPointsValue = "sarc;")
@SuppressWarnings("PMD")
public class LogInfoDirectServiceImpl extends AbstractCubeDirect {
    @Autowired
    private LogInfoServiceServerImpl serviceServer;

    @Override
    public ObjectServer buildServiceServer() {
        return serviceServer;
    }
}
