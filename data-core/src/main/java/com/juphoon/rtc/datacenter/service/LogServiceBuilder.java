package com.juphoon.rtc.datacenter.service;

import com.juphoon.rtc.datacenter.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/21 17:22
 * @Description:
 */
@Component
@Slf4j
public class LogServiceBuilder {

    @Autowired
    private LogService logService;

    @PostConstruct
    public void initTables(){
        logService.initTables();
    }
}
