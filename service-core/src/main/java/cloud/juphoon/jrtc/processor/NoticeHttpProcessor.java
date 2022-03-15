package cloud.juphoon.jrtc.processor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
public class NoticeHttpProcessor extends AbstractEventProcessor implements IConfigurableProcessor<RestTemplate, NoticeHttpProcessor.NoticeConfig> {


    @Autowired
    RestTemplate noticeTemplate;

    NoticeConfig noticeConfig;


    public NoticeHttpProcessor(NoticeConfig noticeConfig) {
        this.noticeConfig = noticeConfig;
        log.info("NoticeHttpProcessor");
    }


    @Override
    public RestTemplate getTemplate() {
        return noticeTemplate;
    }

    @Override
    public NoticeConfig getConfig() {
        return noticeConfig;
    }


    @ConfigurationProperties("notice.config")
    @Data
    @Component
    public static class NoticeConfig {
        private String uri = "http://127.0.0.1:8080"; //uri 或者 域名，负载均衡 nginx应该可以替代。
    }

}
