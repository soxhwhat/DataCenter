package com.juphoon.rtc.datacenter.servicecore.cube.service;

import Common.Exception;
import Common.ObjectServer;
import Common.ServerCall;
import Config.ConfigInfo;
import Config.ConfigurationServer;
import com.juphoon.iron.cube.starter.AbstractCubeEntry;
import com.juphoon.iron.cube.starter.annotation.CubeEntry;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.*;

/**
 * /data/nms/nms1.0/update/Domains.cfg 需要添加配置
 * ConfigurationServerEntry:2
 */
@Setter
@Slf4j
@Component
@ConditionalOnProperty(prefix = DATA_CENTER_CONFIG_PREFIX, value = "log.enabled", havingValue = "true")
//@ConditionalOnProperty(prefix = "iron.cube.entry", value = "enabled", havingValue = "true", matchIfMissing = true)
@CubeEntry(serviceName = "ConfigurationServerEntry", endPointsKey = "ConfigurationServerEntry", endPointsValue = "sarc -p 110;")
@SuppressWarnings("PMD")
public class LogConfigurationServerImpl extends AbstractCubeEntry {
    @Autowired
    private LogDirectService logDirectService;

    @Autowired
    private LogInfoDirectServiceImpl infoDirectService;

    @Override
    public ObjectServer buildServiceServer() {
        return new ConfigurationServer() {
            @Override
            public void getClientConfig_begin(ServerCall __call, ConfigInfo info) throws Exception {
                try {
                    log.info("获取客户端配置:{}", info);
                    Map<String, String> clientConfig = getClientConfig(__call, info);
                    getClientConfig_end(__call, true, clientConfig, System.currentTimeMillis());
                } catch (java.lang.Exception e) {
                    log.error("e", e);
                    getClientConfig_end(__call, false, null, System.currentTimeMillis());
                }
            }
        };
    }

    private Map<String, String> getClientConfig(ServerCall call, ConfigInfo info) {
        List<String> keys = info.keys;
        Map<String, String> configMap = new HashMap<>();
        if (CollectionUtils.isEmpty(keys)) {
            configMap.put(COLLECTION_LOG_ENDPOINT, logDirectService.getDirectObjectId(call));
            configMap.put(COLLECTION_INFO_ENDPOINT, infoDirectService.getDirectObjectId(call));
        } else {
            keys.forEach(endpoint -> getClientConfig(configMap, endpoint, call, info));
        }
        return configMap;
    }

    private void getClientConfig(Map<String, String> configMap, String endpoint, ServerCall call, ConfigInfo info) {
        switch (endpoint) {
            case COLLECTION_LOG_ENDPOINT:
                configMap.put(COLLECTION_LOG_ENDPOINT, logDirectService.getDirectObjectId(call));
                break;
            case COLLECTION_INFO_ENDPOINT:
                configMap.put(COLLECTION_INFO_ENDPOINT, infoDirectService.getDirectObjectId(call));
                break;
            default: {
                configMap.put(COLLECTION_LOG_ENDPOINT, logDirectService.getDirectObjectId(call));
                configMap.put(COLLECTION_INFO_ENDPOINT, infoDirectService.getDirectObjectId(call));
            }

        }
    }

}

