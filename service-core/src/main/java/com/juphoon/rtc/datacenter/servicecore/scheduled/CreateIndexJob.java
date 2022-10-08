package com.juphoon.rtc.datacenter.servicecore.scheduled;

import com.juphoon.rtc.datacenter.servicecore.handle.mongo.AbstractMongoHandler;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.MonitorAcdQueueCountMongoHandler;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.MonitorConcurrentStateMongoHandler;
import com.juphoon.rtc.datacenter.servicecore.property.DataCenterProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Set;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static org.springframework.data.domain.Sort.Direction.ASC;

/**
 * <p>定时添加MongoDB索引</p>
 *
 * @author Jiahui.Huang
 * @date 2022/9/8 14:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Configuration
@Slf4j
@SuppressWarnings("PMD")
@Data
public class CreateIndexJob {
    public static final String TIME_FROM_NUMBER_IDX = "time_from_number_idx";
    public static final String TIME_DOMAIN_APP_FROM_IDX = "time_domain_app_from_idx";
    /**
     * 新建集合索引数为1，用于判断集合已经是否添加自定义索引
     */
    public static final int INIT_INDEX = 1;
    @Autowired
    private DataCenterProperties properties;

    @Autowired
    private MonitorAcdQueueCountMongoHandler monitorAcdQueueCountMongoHandler;

    @Autowired
    private MonitorConcurrentStateMongoHandler monitorConcurrentStateMongoHandler;

    public DataCenterProperties getProperties() {
        return properties;
    }

    Index index = new Index()
            .on("time", ASC)
            .on("domainId", ASC)
            .on("appId", ASC)
            .on("from", ASC)
            .unique().named(TIME_DOMAIN_APP_FROM_IDX);

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    /**
     * 定时添加集合索引逻辑：
     * 1.首先设置定时为每天0点，以及服务启动时
     * 2.获取配置中一次给集合添加索引的天数
     * 3.获取每个handler对应的集合名称以及数据库中的所有集合
     * 4.在配置天数范围内判断集合是否存在，索引是否存在
     *
     * @param handler
     * @param index
     */
    public void createIndex(AbstractMongoHandler handler, Index index) {
        int indexDay = getProperties().getMongoIndex().getIndexDay();
        String collection = handler.collectionName().getName();
        String collectionName;
        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.CHINA)
                .withResolverStyle(ResolverStyle.LENIENT);
        int dayName = Integer.parseInt(timeFormatter.format(LocalDate.now()));
        for (int i = 0; i < indexDay; i++) {
            collectionName = collection + dayName;
            dayName = Integer.parseInt(timeFormatter.format(LocalDate.now().plusDays(i + 1)));
            if (!collectionNames.contains(collectionName)) {
                mongoTemplate.createCollection(collectionName);
            }
            if (mongoTemplate.indexOps(collectionName).getIndexInfo().size() == INIT_INDEX) {
                mongoTemplate.indexOps(collectionName).ensureIndex(index);
            }
        }

    }

    @Scheduled(cron = "${iron.datacenter.mongoIndex.cronIndex:0 0 0 1/1 * ?}")
    @PostConstruct
    public void createConcurrentIndex() {
        createIndex(monitorConcurrentStateMongoHandler, index);
    }

    @Scheduled(cron = "${iron.datacenter.mongoIndex.cronIndex:0 0 0 1/1 * ?}")
    @PostConstruct
    public void createAgentStateIndex() {
        Index index1 = new Index().on("from", ASC).on("uniqueKey", ASC).unique().named(TIME_FROM_NUMBER_IDX);
        createIndex(monitorAcdQueueCountMongoHandler, index1);

    }

}
