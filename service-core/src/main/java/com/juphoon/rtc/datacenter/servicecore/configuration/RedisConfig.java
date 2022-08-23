//package com.juphoon.rtc.datacenter.servicecore.configuration;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * <p>redis配置类</p>
// *
// * @author ke.wang@juphoon.com
// * @date 2022/3/24
// * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
// */
//@Configuration
//public class RedisConfig {
//
//    @Bean("redisTemplate")
//    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        //设置连接工厂
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
//                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
//
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        RedisTemplate<?, ?> template = new RedisTemplate();
//        //设置连接工厂
//        template.setConnectionFactory(redisConnectionFactory);
//        //设置序列化转换
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }
//
//
//}
