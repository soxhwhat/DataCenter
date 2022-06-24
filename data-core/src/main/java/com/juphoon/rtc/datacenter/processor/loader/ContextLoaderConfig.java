package com.juphoon.rtc.datacenter.processor.loader;

import lombok.Data;

/**
 * <p>定时捞事件配置</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 6/24/22 9:18 AM
 */
@Data
public class ContextLoaderConfig {
    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 启动延迟
     */
    private Long initialDelaySeconds = 15L;

    /**
     * 扫描频率
     */
    private Long delaySeconds = 5L;

    /**
     * 每次加载数量
     */
    private int loadSize = 100;
}
