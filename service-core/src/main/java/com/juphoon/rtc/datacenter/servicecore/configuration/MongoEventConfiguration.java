package com.juphoon.rtc.datacenter.servicecore.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Collections;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;

/**
 * <p>Mongodb数据源配置</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 4/1/22 3:02 PM
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.event")
public class MongoEventConfiguration {
    /**
     * 连接字符串
     */
    private String uri;

    @Primary
    @Bean("eventMongoFactory")
    public MongoDatabaseFactory eventMongodbFactory() {
        return new SimpleMongoClientDatabaseFactory(uri);
    }

    /**
     * 设置AutoIndexCreation 等于 true 自动创建索引
     */
    @Primary
    @Bean("eventMongoConverter")
    public MappingMongoConverter eventMongoConverter(@Qualifier("eventMongoFactory") MongoDatabaseFactory factory,
                                                     ApplicationContext applicationContext) throws Exception {
        MongoCustomConversions conversions = new MongoCustomConversions(Collections.emptyList());
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        MongoMappingContext context = new MongoMappingContext();
        mapper.from(true).to(context::setAutoIndexCreation);
        context.setInitialEntitySet(new EntityScanner(applicationContext).scan(Document.class, Persistent.class));
        context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(conversions);
        return mappingConverter;
    }

    @Primary
    @Bean(MONGO_TEMPLATE_EVENT)
    public MongoTemplate eventMongoTemplate(@Qualifier("eventMongoFactory") MongoDatabaseFactory factory,
                                            @Qualifier("eventMongoConverter") MappingMongoConverter converter) {
        return new MongoTemplate(factory, converter);
    }
}
