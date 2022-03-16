package cloud.juphoon.jrtc.processor;

import cloud.juphoon.jrtc.config.HttpClientPoolConfig;
import cloud.juphoon.jrtc.util.HttpClientUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>暂时还没什么用 </p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/3 17:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
public class NoticeHttpProcessor extends AbstractEventProcessor implements IConfigurableProcessor<HttpClientUtil, NoticeHttpProcessor.NoticeConfig> {


    private volatile HttpClientUtil httpClientUtil = null;

    NoticeConfig noticeConfig;


    public NoticeHttpProcessor(NoticeConfig noticeConfig) {
        this.noticeConfig = noticeConfig;
        log.info("NoticeHttpProcessor");
    }


    @Override
    public HttpClientUtil getTemplate(NoticeConfig httpClientPoolConfig) {
        if (httpClientUtil == null) {
            synchronized (this) {
                if (httpClientUtil == null) {
                    httpClientUtil = new HttpClientUtil(noticeConfig);
                }
            }
        }
        return httpClientUtil;
    }

    @Override
    public NoticeConfig getConfig() {
        return noticeConfig;
    }


    /**
     * httpClient 连接池参数等信息在这里配置。
     */
    @ConfigurationProperties("notice.config")
    @Data
    @Component
    public static class NoticeConfig extends HttpClientPoolConfig {
        private String uri = "http://127.0.0.1:8080"; //uri 或者 域名，负载均衡 nginx应该可以替代。
    }

}
