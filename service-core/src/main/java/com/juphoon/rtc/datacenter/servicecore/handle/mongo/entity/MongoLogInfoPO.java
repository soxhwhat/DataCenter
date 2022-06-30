package com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * <p>Mongodb日志存储PO</p>
 *{
 *     "_id" : ObjectId("62ac38152a939a135af8635c"),
 *     "_ver" : 1,
 *     "_timestamp" : NumberLong(1655453788321),
 *     "_source" : {
 *         "tags" : [
 *             "web-sdk",
 *             "webrtc",
 *             "guest",
 *             "6e76d964-3302-4387-a25e-4c2eb9767616",
 *             "23044"
 *         ],
 *         "info" : {
 *             "appKey" : "05c97ce0d0a84d2592334093",
 *             "token" : "6e76d964-3302-4387-a25e-4c2eb9767616",
 *             "socketAddress" : "https://192.168.15.72:10047/webrtc",
 *             "userAgent" : "Mozilla/5.0 (iPhone; CPU iPhone OS 13_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.2 Mobile/15E148 Safari/604.1",
 *             "sdkType" : "webrtc",
 *             "sdkVersion" : "2.5.13-iteration-2.5-stable",
 *             "sdkBuildTime" : "Fri Jun 10 2022 16:11:05 GMT+0800 (China Standard Time)",
 *             "sdkGitHash" : "29a4651b01af85fd417522f9d43a430dcc0bb4fd",
 *             "deviceId" : "Pf5TJtI4kG9oC79J",
 *             "userId" : "g1htLNbMkYVEyV77"
 *         }
 *     },
 *     "_type" : "sdk:webrtc:guest"
 * }
 * @author ajian.zheng@juphoon.com
 * @date 6/14/22 23:26 PM
 */
//@Document("test_log_#{new java.text.SimpleDateFormat(\"yyyyMMdd\").format(new java.util.Date())}")
@Document("info_collection")
@Getter
@Setter
public class MongoLogInfoPO {
    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 时间戳
     */
    @Indexed(direction = IndexDirection.DESCENDING, expireAfterSeconds = 180 * 24 * 60 * 60)
    @Field("_timestamp")
    @JsonProperty("_timestamp")
    private Long timestamp;

    @Field("_ver")
    @JsonProperty("_ver")
    private Integer ver;

    @Field("_source")
    @JsonProperty("_source")
    private Source source;

    @Data
    public static class Source {
        @Indexed
        private List<String> tags;

        @Field("_type")
        private String type;

        private Map<String, Object> info;
    }

    /**
     * 其他参数
     */
    @SneakyThrows
    public static MongoLogInfoPO fromString(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(data, MongoLogInfoPO.class);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MongoLogInfoPO.class.getSimpleName() + "[", "]")
                .add("domainId=" + domainId)
                .add("appId=" + appId)
                .add("timestamp=" + timestamp)
                .add("_ver=" + ver)
                .add("_source=" + source)
                .toString();
    }
}
