package com.juphoon.rtc.datacenter.cube.service;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import DataCollection.Info;
import DataCollection.InfoIdCollectionServiceServer;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import com.juphoon.rtc.datacenter.entity.po.cube.InfoBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;


@Setter
@Slf4j
@Component
@CubeService(serviceName = "#InfoIdCollectionServer")
@SuppressWarnings("PMD")
@ConditionalOnProperty(prefix = "iron.cube.entry", value = "enabled", havingValue = "true", matchIfMissing = true)
public class CollectionInfoIdServiceServerServiceImpl extends AbstractCubeService {//extends InfoIdCollectionServiceServer {

    @Autowired
    InfoCollectionServerProcess process;

    @Override
    public ObjectServer buildServiceServer() {
        return new InfoIdCollectionServiceServer() {
            @Override
            public void getInfoId_begin(ServerCall __call, Info info) throws Exception {
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

