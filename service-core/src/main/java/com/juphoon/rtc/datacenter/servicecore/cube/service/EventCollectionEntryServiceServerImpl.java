package com.juphoon.rtc.datacenter.servicecore.cube.service;

import Common.ObjectServer;
import com.juphoon.iron.cube.starter.AbstractCubeEntry;
import com.juphoon.iron.cube.starter.annotation.CubeEntry;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * <p>EventCollectionServer RPC服务实现</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2022-03-25
 */
@Slf4j
@Setter
@ConditionalOnProperty(prefix = "iron.cube.entry", value = "enabled", havingValue = "true", matchIfMissing = true)
@CubeEntry(serviceName = "EventCollectionEntryServer", endPointsKey = "EventCollectionEntryServer", endPointsValue = "sarc -p 110;")
@SuppressWarnings("PMD")
public class EventCollectionEntryServiceServerImpl extends AbstractCubeEntry {

    @Autowired
    private EventCollectionServer eventCollectionServer;

    @Override
    public ObjectServer buildServiceServer() {
        return eventCollectionServer;
    }
}