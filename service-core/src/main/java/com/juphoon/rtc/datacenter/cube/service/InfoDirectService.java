package com.juphoon.rtc.datacenter.cube.service;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import DataCollection.Info;
import DataCollection.InfoIdCollectionServiceServer;
import com.juphoon.iron.cube.starter.AbstractCubeDirect;
import com.juphoon.iron.cube.starter.annotation.CubeDirect;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import com.juphoon.rtc.datacenter.entity.po.cube.InfoBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

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
public class InfoDirectService extends AbstractCubeDirect {
    @Autowired
    InfoCollectionServerProcess process;

    @Override
    public ObjectServer buildServiceServer() {
        return new InfoIdCollectionServiceServer() {
            @Override
            public void getInfoId_begin(ServerCall __call, Info info) throws Exception {
                log.info("infoId_begin:{}",info);
                InfoBO infoBO = new InfoBO();
                try {
                    infoBO.setTimestamp(info.timestamp);
                    infoBO.setParams(info.params);
                    infoBO.setType(info.type);
                    infoBO.setTags(info.tags);
                    process.process(__call, Arrays.asList(infoBO));
                    getInfoId_end(__call, true, infoBO.getId());
                } catch (java.lang.Exception e) {
                    __call.setReason(e.getMessage());
                    getInfoId_end(__call, false, null);
                }
            }

        };
    }
}
