package com.juphoon.rtc.datacenter.servicecore.configuration;

import org.springframework.context.annotation.Configuration;

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
