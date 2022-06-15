package com.juphoon.rtc.datacenter.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.*;

/**
 * <p>Mongodb数据源配置</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 4/1/22 3:02 PM
 */
@Configuration
public class MongoConfiguration {





//    @Bean("mdEventMongoProperties")
//    @ConfigurationProperties(prefix = "spring.data.mongodb.md")
//    public MongoProperties getMdEventProperties() {
//        return new MongoProperties();
//    }
//
//    @Bean("mdMongodbFactory")
//    public MongoDatabaseFactory mdEventMongodbFactory(@Qualifier("mdEventMongoProperties") MongoProperties properties) {
//        return new SimpleMongoClientDatabaseFactory(properties.getUri());
//    }
//
//    @Bean(MONGO_TEMPLATE_MD)
//    public MongoTemplate mdEventMongoTemplate() {
//        return new MongoTemplate(mdEventMongodbFactory(getMdEventProperties()));
//    }

//    @Bean("logEventMongoProperties")
//    @ConfigurationProperties(prefix = "spring.data.mongodb.log")
//    public MongoProperties getLogEventProperties() {
//        return new MongoProperties();
//    }
//
//    @Bean("logMongodbFactory")
//    public MongoDatabaseFactory logEventMongodbFactory(@Qualifier("logEventMongoProperties") MongoProperties properties) {
//        return new SimpleMongoClientDatabaseFactory(properties.getUri());
//    }
//
//    @Bean(MONGO_TEMPLATE_LOG)
//    public MongoTemplate logEventMongoTemplate() {
//        return new MongoTemplate(logEventMongodbFactory(getLogEventProperties()));
//    }
}
