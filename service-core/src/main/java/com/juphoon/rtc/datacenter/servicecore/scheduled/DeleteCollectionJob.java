package com.juphoon.rtc.datacenter.servicecore.scheduled;

import com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.servicecore.property.DataCenterProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_MD;
import static com.juphoon.rtc.datacenter.servicecore.api.TheaConstant.DATA_DELIMITER;

/**
 * <p>定期清理MongoDB过期集合</p>
 *
 * @author Jiahui.Huang
 * @date 2022/8/29 16:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Configuration
@Slf4j
@Data
public class DeleteCollectionJob {

    @Autowired
    private DataCenterProperties properties;

    public DataCenterProperties getProperties() {
        return properties;
    }

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate eventMongoTemplate;

    @Autowired
    @Qualifier(MONGO_TEMPLATE_MD)
    private MongoTemplate mdMongoTemplate;




    @Scheduled(cron = "${iron.datacenter.clear.cronEvent:0 0 0 * * ?}")
    public void deleteEventData() {
        int expireEvent = getProperties().getClear().getExpireEvent();
        deleteCollection(expireEvent, eventMongoTemplate);
    }

    @Scheduled(cron = "${iron.datacenter.clear.cronMd:0 0 12 * * ?}")
    public void deleteMdData() {
        int expireMd = getProperties().getClear().getExpireMd();
        deleteCollection(expireMd, mdMongoTemplate);
    }

    public void deleteCollection(int expire, MongoTemplate mongoTemplate) {
        Duration dataExpire = Duration.ofDays(expire);

        int expireDay = Integer.parseInt(DateFormatUtils.format(System.currentTimeMillis() - dataExpire.toMillis(), "yyyyMMdd"));

        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        collectionNames.forEach(collectionName -> {
            if (collectionName.contains(DATA_DELIMITER)) {
                String substring = collectionName.substring(collectionName.lastIndexOf("_") + 1);
                if (substring.compareTo(Integer.toString(expireDay)) < 0) {
                    mongoTemplate.dropCollection(collectionName);
                    log.info("delete collection {}", collectionName);
                }
            }
        });
    }
}
