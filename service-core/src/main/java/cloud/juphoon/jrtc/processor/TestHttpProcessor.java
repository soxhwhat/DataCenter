package cloud.juphoon.jrtc.processor;

import cloud.juphoon.jrtc.config.HttpClientPoolConfig;
import cloud.juphoon.jrtc.util.HttpClientUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>测试多例httpClient </p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/3 17:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
public class TestHttpProcessor extends AbstractEventProcessor implements IConfigurableProcessor<HttpClientUtil, TestHttpProcessor.NoticeConfig2> {


    private volatile HttpClientUtil httpClientUtil = null;

    NoticeConfig2 noticeConfig;


    public TestHttpProcessor(NoticeConfig2 noticeConfig) {
        this.noticeConfig = noticeConfig;
        log.info("TestHttpProcessor");
    }


    @Override
    public HttpClientUtil getTemplate(NoticeConfig2 noticeConfig) {
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
    public NoticeConfig2 getConfig() {
        return noticeConfig;
    }


    @ConfigurationProperties("notice2.config")
    @Data
    @Component
    public static class NoticeConfig2 extends HttpClientPoolConfig {
        private String uri = "http://127.0.0.1:8080"; //uri 或者 域名，负载均衡 nginx应该可以替代。

    }

}
