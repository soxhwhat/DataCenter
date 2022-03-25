package com.juphoon.rtc.datacenter.cube.notice;

import Common.ObjectServer;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author rongbin.huang
 * @create 2019-12-16 4:43 PM
 **/
@Component
@CubeService(serviceName = "#AuthServer")
public class AuthServerServerManager extends AbstractCubeService {


    @Autowired
    private AuthServerServerImpl authServerServer;

    @Override
    public ObjectServer buildServiceServer() {
        return authServerServer;
    }
}
