package com.juphoon.rtc.datacenter.processor;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.handler.AbstractHttpEventHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <p>通用HTTP连接处理器</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 10:39 AM
 */
@Slf4j
@Component
@Scope("prototype")
public class HttpClientEventProcessor extends AbstractHttpEventProcessor {

    private HttpClientEventProcessor.Config config;
    private int counter = 0;

    @Autowired
    private RestTemplate restTemplate;

    public HttpClientEventProcessor() {
    }

    @Override
    public boolean exchange(EventContext ec, AbstractHttpEventHandler handler) {
        return handler.exchange(getUrl(), restTemplate, ec);
    }

    /**
     * 设置配置文件
     *
     * @param config
     */
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * 轮询获取host
     *
     * @return
     */
    private String getUrl() {
        return config.getHosts().get(counter++ % config.getHosts().size());
    }

    @Getter
    @Setter
    public static class Config {
        /**
         * 目标地址
         * target = http(s)://ip:port/target,http(s)://ip:port/target,
         * target = http(s)://domain/target
         */
        private List<String> hosts;
    }

    /*
    // for test
    @Override
    public void process(EventContext ec) {
        getEventHandlers().forEach(h -> {
            try {
                h.handle(ec);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    */
}
