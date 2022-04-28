package com.juphoon.rtc.datacenter.configuration;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * <p>Mongodb数据源配置</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 4/1/22 3:02 PM
 */
@Configuration
public class MongoConfiguration {

    @Primary
    @Bean("eventMongoProperties")
    @ConfigurationProperties(prefix = "event.spring.data.mongodb")
    public MongoProperties getEventProperties() {
        return new MongoProperties();
    }

    @Primary
    @Bean("eventMongodbFactory")
    public MongoDatabaseFactory eventMongodbFactory(MongoProperties properties) {
        return new SimpleMongoClientDatabaseFactory(properties.getUri());
    }

    @Primary
    @Bean("eventMongoTemplate")
    public MongoTemplate eventMongoTemplate() {
        return new MongoTemplate(eventMongodbFactory(getEventProperties()));
    }

    @Bean("recordMongoProperties")
    @ConfigurationProperties(prefix = "record.spring.data.mongodb")
    public MongoProperties getRecordProperties() {
        return new MongoProperties();
    }

    @Bean("recordMongodbFactory")
    public MongoDatabaseFactory recordMongodbFactory(MongoProperties properties) {
        return new SimpleMongoClientDatabaseFactory(properties.getUri());
    }

    @Bean("recordMongoTemplate")
    public MongoTemplate recordMongoTemplate() {
        return new MongoTemplate(recordMongodbFactory(getRecordProperties()));
    }

    @Bean("mdEventMongoProperties")
    @ConfigurationProperties(prefix = "md.spring.data.mongodb")
    public MongoProperties getMdEventProperties() {
        return new MongoProperties();
    }

    @Bean("mdMongodbFactory")
    public MongoDatabaseFactory mdEventMongodbFactory(MongoProperties properties) {
        return new SimpleMongoClientDatabaseFactory(properties.getUri());
    }

    @Bean("mdMongoTemplate")
    public MongoTemplate mdEventMongoTemplate() {
        return new MongoTemplate(mdEventMongodbFactory(getMdEventProperties()));
    }
}
