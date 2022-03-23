package com.juphoon.rtc.datacenter.property;

import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>dataCenter默认配置</p>
 * java配置的优先级低于yml配置；如果yml配置不存在，会采用java配置
 *
 * @author ajian.zheng
 * @date 2022-03-22
 */
@Component
@ConfigurationProperties(prefix = JrtcDataCenterConstant.DATA_CENTER_CONFIG_PREFIX)
@Data
public class JrtcDataCenterProperties {

    private Agree agree = new Agree();

    @Data
    public static class Agree {
        /**
         * 赞同通知开关
         */
        private boolean enabled = false;

        /**
         * 赞同主机列表
         */
        private List<String> hosts;
    }

}

